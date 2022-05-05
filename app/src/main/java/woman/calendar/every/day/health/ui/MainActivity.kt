package woman.calendar.every.day.health.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val ACTION_SHOW_NOTIFICATION_SCREEN = "ACTION_SHOW_NOTIFICATION_SCREEN"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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