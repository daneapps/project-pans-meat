package com.dane.teamsnap.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dane.teamsnap.databinding.AddYourPlayersRowBinding
import com.dane.teamsnap.databinding.AdminRowBinding
import com.dane.teamsnap.databinding.PeopleOverviewFragmentBinding
import com.dane.teamsnap.databinding.SpaceRowBinding
import com.dane.teamsnap.fab.FabState
import com.dane.teamsnap.fab.ShowFab
import com.dane.teamsnap.util.ViewBindingHolder
import com.dane.teamsnap.view.FabFragment
import com.dane.teamsnap.view.SpaceRowViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PeopleOverviewFragment :
    FabFragment(),
    ViewBindingHolder<PeopleOverviewFragmentBinding> {
    override var binding: PeopleOverviewFragmentBinding? = null
    private val viewModel: PeopleOverviewViewModel by viewModels()
    private var adapter: PeopleOverViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        with(PeopleOverviewFragmentBinding.inflate(inflater, container, false)) {
            registerBinding(this, viewLifecycleOwner)
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PeopleOverViewAdapter()
        with(requireBinding().peopleRecyclerView) {
            adapter = this@PeopleOverviewFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    resetVisibilities()
                    when (it) {
                        PeopleOverviewLoading -> requireBinding().peopleOverviewLoading.isVisible =
                            true
                        is PeopleOverviewList -> {
                            requireBinding().peopleRecyclerView.isVisible = true
                            adapter?.submitList(it.listData)
                        }
                    }
                }
            }
        }
        viewModel.initialize(viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    override fun nextFabState(): FabState {
        return ShowFab("com.people.PEOPLE_OVERVIEW", listOf())
    }

    private fun resetVisibilities() {
        requireBinding().peopleRecyclerView.isVisible = false
        requireBinding().peopleOverviewLoading.isVisible = false
    }

    private inner class PeopleOverViewAdapter :
        ListAdapter<PeopleOverviewListItem, RecyclerView.ViewHolder>(object :
                DiffUtil.ItemCallback<PeopleOverviewListItem>() {
                override fun areItemsTheSame(
                    oldItem: PeopleOverviewListItem,
                    newItem: PeopleOverviewListItem
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: PeopleOverviewListItem,
                    newItem: PeopleOverviewListItem
                ): Boolean {
                    return oldItem == newItem
                }
            }) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                ADD_YOUR_PLAYER_ROW -> AddYourPlayersViewHolder(
                    AddYourPlayersRowBinding.inflate(
                        LayoutInflater.from(requireContext()),
                        parent,
                        false
                    )
                )
                ADMIN_ROW -> AdminViewHolder(
                    AdminRowBinding.inflate(
                        LayoutInflater.from(requireContext()),
                        parent,
                        false
                    )
                )
                SPACE_ROW -> SpaceRowViewHolder(
                    SpaceRowBinding.inflate(
                        LayoutInflater.from(requireContext()),
                        parent,
                        false
                    )
                )
                else -> throw RuntimeException("Unknown view type")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (getItemViewType(position) == ADMIN_ROW) {
                (holder as AdminViewHolder).bind(currentList[position] as Admin)
            }
        }

        override fun getItemViewType(position: Int): Int {
            return when (currentList[position]) {
                AddYourPlayers -> ADD_YOUR_PLAYER_ROW
                is Admin -> ADMIN_ROW
                is Space -> SPACE_ROW
            }
        }

        override fun getItemCount(): Int {
            return currentList.size
        }
    }

    private inner class AddYourPlayersViewHolder(binding: AddYourPlayersRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    private inner class AdminViewHolder(private val binding: AdminRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(admin: Admin) {
            binding.adminRowName.text = admin.name
            binding.adminRowType.text = admin.adminType
        }
    }

    private companion object {
        const val ADD_YOUR_PLAYER_ROW = 0
        const val ADMIN_ROW = 1
        const val SPACE_ROW = 2
    }
}
