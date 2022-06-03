package com.period.tracker.natural.cycles.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.ActivityMainBinding
import com.period.tracker.natural.cycles.domain.usecase.notification.OnOffEverydayNotificationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val mainViewModel: MainViewModel by viewModel()
    private val onOffEverydayNotificationUseCase: OnOffEverydayNotificationUseCase by inject()

    companion object {
        const val ACTION_SHOW_NOTIFICATION_SCREEN = "ACTION_SHOW_NOTIFICATION_SCREEN"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        mainViewModel.setUser(auth.currentUser)

        lifecycleScope.launch(Dispatchers.IO) { onOffEverydayNotificationUseCase.execute() }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home,
                R.id.navigation_articles -> navView.visibility = View.VISIBLE
                else -> navView.visibility = View.GONE
            }
        }
        navigate(intent, navController)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment).navController
        navigate(intent, navController)
    }

    private fun navigate(intent: Intent?, navController: NavController) {
        when (intent?.action) {
            ACTION_SHOW_NOTIFICATION_SCREEN -> {
                navController.navigate(R.id.action_global_to_notificationScreen)
            }
        }
    }

}