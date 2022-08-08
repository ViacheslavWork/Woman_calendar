package com.natural.cycles.period.tracker.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.PurchaseInfo
import com.anjlab.android.iab.v3.SkuDetails
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.ActivityMainBinding
import com.natural.cycles.period.tracker.domain.usecase.notification.OnOffEverydayNotificationUseCase
import com.natural.cycles.period.tracker.preferences.PremiumStatusPreferences
import com.natural.cycles.period.tracker.ui.subscription.Subscription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(), BillingProcessor.IBillingHandler {
    private lateinit var auth: FirebaseAuth
    private val mainViewModel: MainViewModel by viewModel()
    private val onOffEverydayNotificationUseCase: OnOffEverydayNotificationUseCase by inject()
    private val premiumStatusPreferences: PremiumStatusPreferences by inject()

    companion object {
        const val ACTION_SHOW_NOTIFICATION_SCREEN = "ACTION_SHOW_NOTIFICATION_SCREEN"
        private const val LICENCE_KEY = R.string.licence_key
        private const val SUBSCRIPTION_ID_MONTH = "month_subscription"
    }

    private var bp: BillingProcessor? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBillingProcessor()

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

        observeSubscriptionEvent()
    }

    private fun observeSubscriptionEvent() {
        mainViewModel.subscriptionEvent.observe(this) {
            it.getContentIfNotHandled()?.let {
                if (bp?.isSubscriptionUpdateSupported == true) {
                    bp?.subscribe(this, SUBSCRIPTION_ID_MONTH)
                }
//                premiumStatusPreferences.setPremiumStatus(isPremium = true)
//                mainViewModel.updatePremiumStatus()
            }
        }
    }

    private fun initBillingProcessor() {
        bp = BillingProcessor.newBillingProcessor(
            this, getString(LICENCE_KEY), this
        )
        bp?.initialize()
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

    //billing
    override fun onProductPurchased(productId: String, details: PurchaseInfo?) {
        updateSubscriptionsInfo()
    }

    override fun onPurchaseHistoryRestored() {
        lifecycleScope.launch(Dispatchers.IO) {
            for (i in 1..2) {
                delay(1000)
                updateSubscriptionsInfo()
            }
        }
    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
        updateSubscriptionsInfo()
    }

    override fun onBillingInitialized() {
        updateSubscriptionsInfo()
    }

    private fun updateSubscriptionsInfo() {
        Timber.d("Update sub info")
        updateSubscriptionStatus()
        updateSubscriptionsPrice()
    }

    private fun hasSubscription(): Boolean {
        val isMonthSubscribed = bp?.isSubscribed(SUBSCRIPTION_ID_MONTH)
        return isMonthSubscribed ?: false
    }

    private fun updateSubscriptionStatus() {
        premiumStatusPreferences.setPremiumStatus(isPremium = hasSubscription())
        mainViewModel.updatePremiumStatus()
    }


    private fun updateSubscriptionsPrice() {
        bp?.getSubscriptionListingDetailsAsync(
            SUBSCRIPTION_ID_MONTH,
            object : BillingProcessor.ISkuDetailsResponseListener {
                override fun onSkuDetailsResponse(products: MutableList<SkuDetails>?) {
                    if (products?.size != 0) {
                        val product = products?.get(0)
                        val monthPrice = product?.priceText
                        monthPrice?.let { mainViewModel.setPrice(Subscription.MONTH, it) }
                    }
                }

                override fun onSkuDetailsError(error: String?) {
                }
            })
    }


    override fun onDestroy() {
        bp?.release()
        super.onDestroy()
    }

}