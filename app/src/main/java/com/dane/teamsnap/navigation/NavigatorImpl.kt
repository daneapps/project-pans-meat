package com.dane.teamsnap.navigation

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ActivityRetainedScoped
class NavigatorImpl @Inject constructor() : Navigator {
    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)

    override fun navigate(event: NavigationEvent) {
        _navigationEvent.value = event
    }

    override fun getNavigationEvents(): StateFlow<NavigationEvent?> {
        return _navigationEvent
    }
}
