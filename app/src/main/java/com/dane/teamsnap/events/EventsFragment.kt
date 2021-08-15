package com.dane.teamsnap.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dane.teamsnap.databinding.EventFragmentBinding
import com.dane.teamsnap.fab.FabState
import com.dane.teamsnap.fab.ShowFab
import com.dane.teamsnap.util.ViewBindingHolder
import com.dane.teamsnap.util.launchAndRepeatWithViewLifecycle
import com.dane.teamsnap.view.FabFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EventsFragment : FabFragment(), ViewBindingHolder<EventFragmentBinding> {
    private val viewModel: EventsViewModel by viewModels()

    override var binding: EventFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        with(EventFragmentBinding.inflate(inflater, container, false)) {
            registerBinding(this, viewLifecycleOwner)
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchAndRepeatWithViewLifecycle {
            fabViewModel.fabOption.collect {
                val fabState = it ?: return@collect
                if (fabState.consumed) {
                    return@collect
                }
                if (fabState.requesterId == FAB_ID) {
                    fabState.consumed = true
                    viewModel.requestNewEvent()
                }
            }
        }
    }

    override fun nextFabState(): FabState {
        return ShowFab(FAB_ID, listOf())
    }

    private companion object {
        const val FAB_ID = "com.events.EVENTS_FRAG_FAB"
    }
}
