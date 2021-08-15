package com.dane.teamsnap.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dane.teamsnap.databinding.HomeFragmentBinding
import com.dane.teamsnap.fab.FabState
import com.dane.teamsnap.fab.ShowFab
import com.dane.teamsnap.view.FabFragment

class DashboardFragment : FabFragment() {
    private var homeFragmentBinding: HomeFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        with(HomeFragmentBinding.inflate(inflater, container, false)) {
            homeFragmentBinding = this
            return root
        }
    }

    override fun nextFabState(): FabState {
        return ShowFab("com.dashboard.DASHBOARD_FRAGMENT", listOf())
    }
}
