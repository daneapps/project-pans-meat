package com.dane.teamsnap.fab

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject

sealed class FabState
// fab options would be used to configure the fab. it is not used and is just a list of strings,
// though it would presumably represent the id, icon, and text of any fab options
data class ShowFab(val requesterId: String, val fabOptions: List<String>) : FabState()
data class HideFab(val requesterId: String) : FabState()
data class FabOptionRequest(val requestId: String, val requesterId: String, val option: String) {
    var consumed = false
}

/**
 * The purpose of this is to decouple components that want to show a FAB, and the owner of the FAB.
 * If views can each show their own FAB, and it looks OK during transitions, then this isn't
 * strictly necessary, however the FAB appearing/disappearing animation was desired.
 */
@HiltViewModel
class FabViewModel @Inject constructor() : ViewModel() {
    private val _fabState = MutableStateFlow<FabState?>(null)
    val fabState: StateFlow<FabState?> = _fabState

    private val _fabOption = MutableStateFlow<FabOptionRequest?>(null)
    val fabOption: StateFlow<FabOptionRequest?> = _fabOption

    fun updateFabState(fabState: FabState) {
        _fabState.value = fabState
    }

    fun onOptionSelected(requesterId: String, option: String) {
        _fabOption.value = FabOptionRequest(UUID.randomUUID().toString(), requesterId, option)
    }
}
