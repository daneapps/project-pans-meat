package com.dane.teamsnap.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dane.teamsnap.databinding.NewEventFragmentBinding
import com.dane.teamsnap.navigation.Back
import com.dane.teamsnap.navigation.Navigator
import com.dane.teamsnap.util.ViewBindingHolder
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class NewEventFragment : Fragment(), ViewBindingHolder<NewEventFragmentBinding> {
    override var binding: NewEventFragmentBinding? = null

    /**
     * No view model for demo purposes, so call the navigator directly
     */
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        with(NewEventFragmentBinding.inflate(inflater, container, false)) {
            registerBinding(this, viewLifecycleOwner)
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireBinding().newEventToolbar.setNavigationOnClickListener {
            navigator.navigate(Back(UUID.randomUUID().toString()))
        }
    }
}
