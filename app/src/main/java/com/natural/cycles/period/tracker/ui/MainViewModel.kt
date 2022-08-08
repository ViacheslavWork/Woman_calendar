package com.natural.cycles.period.tracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.natural.cycles.period.tracker.domain.usecase.firebase.days.DownloadDaysFromFirebaseUseCase
import com.natural.cycles.period.tracker.preferences.PremiumStatusPreferences
import com.natural.cycles.period.tracker.ui.subscription.Subscription
import com.natural.cycles.period.tracker.ui.subscription.SubscriptionEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class MainViewModel(
    private val downloadDaysFromFirebaseUseCase: DownloadDaysFromFirebaseUseCase
) : ViewModel(), KoinComponent {

    private val premiumStatusPreferences: PremiumStatusPreferences by inject()

    private val _subscriptionEvent = MutableLiveData<SubscriptionEvent>()
    val subscriptionEvent: LiveData<SubscriptionEvent> = _subscriptionEvent

    private val _monthPrice = MutableLiveData<String?>(null)
    val monthPrice: LiveData<String?> = _monthPrice

    private val _isPremium = MutableLiveData<Boolean>(true)
    val isPremium: LiveData<Boolean> = _isPremium

    private val _user = MutableLiveData<FirebaseUser?>(null)
    val user: LiveData<FirebaseUser?> = _user

    fun setUser(user: FirebaseUser?) {
        _user.postValue(user)
    }

    fun setPrice(subscription: Subscription, price: String) {
        when (subscription) {
            Subscription.MONTH -> _monthPrice.postValue(price)
            Subscription.YEAR -> TODO()
        }
    }

    fun updatePremiumStatus() {
        Timber.d("IsPremium: ${premiumStatusPreferences.userHasPremiumStatus()}")
        _isPremium.postValue(premiumStatusPreferences.userHasPremiumStatus())
    }

    fun handleEvent(subscriptionEvent: SubscriptionEvent) {
        _subscriptionEvent.postValue(subscriptionEvent)
    }
}