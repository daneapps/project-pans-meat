package com.dane.teamsnap.navigation

import android.os.Bundle
import kotlinx.coroutines.flow.StateFlow

sealed class NavigationEvent {
    var consumed = false
}

data class NavigateTo(val goto: Int, val bundle: Bundle? = null, val requestId: String? = null) : NavigationEvent()
data class Back(val requestId: String) : NavigationEvent()
interface Navigator {
    fun navigate(event: NavigationEvent)
    fun getNavigationEvents(): StateFlow<NavigationEvent?>
}
