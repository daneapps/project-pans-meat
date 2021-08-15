package com.dane.teamsnap

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dane.teamsnap.databinding.ActivityMainBinding
import com.dane.teamsnap.navigation.Back
import com.dane.teamsnap.navigation.NavigateTo
import com.dane.teamsnap.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                navigator.getNavigationEvents().collect { event ->
                    if (event == null || event.consumed) {
                        return@collect
                    }
                    event.consumed = true
                    when (event) {
                        is Back -> if (navController?.popBackStack() == false) {
                            finish()
                        }
                        is NavigateTo -> navController?.navigate(event.goto)
                    }
                }
            }
        }
    }
}
