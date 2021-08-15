package com.dane.teamsnap.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dane.teamsnap.databinding.ChatFragmentBinding
import com.dane.teamsnap.fab.FabState
import com.dane.teamsnap.fab.ShowFab
import com.dane.teamsnap.view.FabFragment

class ChatFragment : FabFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ChatFragmentBinding.inflate(inflater, container, false).root
    }

    override fun nextFabState(): FabState {
        return ShowFab("com.chat.CHAT_FRAGMENT", listOf())
    }
}
