package com.dane.teamsnap.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dane.teamsnap.R
import com.dane.teamsnap.databinding.MainFragmentBinding
import com.dane.teamsnap.fab.FabViewModel
import com.dane.teamsnap.fab.HideFab
import com.dane.teamsnap.fab.ShowFab
import com.dane.teamsnap.navigation.Navigator
import com.dane.teamsnap.util.ViewBindingHolder
import com.dane.teamsnap.util.launchAndRepeatWithViewLifecycle
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(), ViewBindingHolder<MainFragmentBinding> {
    override var binding: MainFragmentBinding? = null

    private val fabViewModel: FabViewModel by viewModels()

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this is just for the exit transition of a sub-fragment, as there is only one transition.
        // in production, a better way would be needed, as not every sub-fragment would necessarily
        // require this set, especially for shared element transitions, etc.
        exitTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z,
            true
        )
        reenterTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z,
            false
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        with(MainFragmentBinding.inflate(inflater, container, false)) {
            registerBinding(this, viewLifecycleOwner)
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        with(requireBinding().bottomNav) {
            setupWithNavController(navHostFragment.navController)
            setOnItemReselectedListener {
                // prevent navigation from creating same fragment
            }
        }
        requireBinding().mainToolbar.title = "Dane's Tennis Team"
        with(requireBinding().fab) {
            requireBinding().fab.setOnClickListener {
                val fabState = this.tag as? ShowFab ?: return@setOnClickListener
                fabViewModel.onOptionSelected(fabState.requesterId, "")
            }
        }

        launchAndRepeatWithViewLifecycle {
            fabViewModel.fabState.collect {
                val fabState = it ?: return@collect
                when (fabState) {
                    is HideFab -> requireBinding().fab.hide()
                    is ShowFab -> {
                        requireBinding().fab.tag = fabState
                        requireBinding().fab.show()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // with more time, this could be handled in either the activity, or a subclass of fragment.
        // the purpose is to clear the window drawable, otherwise odd transitions occur as
        // the window drawable can bleed through.
        activity?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }
}
