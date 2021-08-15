package com.dane.teamsnap.events

import androidx.lifecycle.ViewModel
import com.dane.teamsnap.R
import com.dane.teamsnap.navigation.NavigateTo
import com.dane.teamsnap.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(private val navigator: Navigator) : ViewModel() {

    fun requestNewEvent() {
        navigator.navigate(
            NavigateTo(
                R.id.action_mainFragment_to_newEventFragment,
                requestId = UUID.randomUUID().toString()
            )
        )
    }
}
