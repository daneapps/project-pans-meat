package com.dane.teamsnap.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dane.teamsnap.fab.FabState
import com.dane.teamsnap.fab.FabViewModel
import com.dane.teamsnap.fab.HideFab
import java.util.UUID

open class FabFragment : Fragment() {
    protected val fabViewModel: FabViewModel by viewModels(
        ownerProducer = { requireParentFragment().requireParentFragment() }
    )

    override fun onStart() {
        super.onStart()
        fabViewModel.updateFabState(nextFabState())
    }

    open fun nextFabState(): FabState {
        return HideFab(UUID.randomUUID().toString())
    }
}
