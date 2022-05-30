package com.period.tracker.natural.cycles.notifications

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.domain.usecase.GetDailyNotificationDataUseCase

class EverydayNotificationService : LifecycleService() {
    private val getDailyNotificationDataUseCase: GetDailyNotificationDataUseCase by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        lifecycleScope.launch {
            getDailyNotificationDataUseCase.execute(LocalDate.now())?.let {
                val notification = EverydayNotification(applicationContext).show(
                    date = LocalDate.now(),
                    content = it.notificationText
                )
//                startForeground(1, notification)
//                stopSelf()
            }
        }
        return START_STICKY
    }
}