package com.dane.teamsnap.people

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PeopleOverviewState
object PeopleOverviewLoading : PeopleOverviewState()
data class PeopleOverviewList(val listData: List<PeopleOverviewListItem>) : PeopleOverviewState()

sealed class PeopleOverviewListItem
object AddYourPlayers : PeopleOverviewListItem()
data class Space(val position: Int) : PeopleOverviewListItem()
data class Admin(val id: String, val name: String, val adminType: String) :
    PeopleOverviewListItem()

@HiltViewModel
class PeopleOverviewViewModel @Inject constructor(private val model: PeopleModel) :
    ViewModel(),
    LifecycleObserver {
    private val _uiState = MutableStateFlow<PeopleOverviewState>(PeopleOverviewLoading)
    val uiState: StateFlow<PeopleOverviewState> = _uiState

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun refresh() {
        viewModelScope.launch {
            val listData = mutableListOf<PeopleOverviewListItem>()
            model.getPlayers() // ignore result for demonstration purposes
            listData.add(AddYourPlayers)

            listData.add(Space(listData.lastIndex + 1))

            val owner =
                model.getOwners().first() // always assume one returned for demonstration purposes
            listData.add(Admin(owner.id, owner.name, owner.type))
            _uiState.value = PeopleOverviewList(listData)
        }
    }

    fun initialize(viewLifecycleOwner: LifecycleOwner) {
        viewLifecycleOwner.lifecycle.addObserver(this)
    }
}
