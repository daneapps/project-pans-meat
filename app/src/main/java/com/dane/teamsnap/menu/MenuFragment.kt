package com.dane.teamsnap.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dane.teamsnap.databinding.MenuFragmentBinding
import com.dane.teamsnap.view.FabFragment

class MenuFragment : FabFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MenuFragmentBinding.inflate(inflater, container, false).root
    }
}
