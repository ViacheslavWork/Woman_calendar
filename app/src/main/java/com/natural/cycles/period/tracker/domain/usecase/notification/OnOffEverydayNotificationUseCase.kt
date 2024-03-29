package com.natural.cycles.period.tracker.domain.usecase.notification

import com.natural.cycles.period.tracker.domain.usecase.periods.GetMinCountOfPeriodsUseCase
import com.natural.cycles.period.tracker.notifications.EverydayNotificationScheduler
import com.natural.cycles.period.tracker.preferences.NotificationSchedulerPreferences
import com.natural.cycles.period.tracker.utils.Constants
import timber.log.Timber

class OnOffEverydayNotificationUseCase(
    private val everydayNotificationScheduler: EverydayNotificationScheduler,
    private val getMinCountOfPeriodsUseCase: GetMinCountOfPeriodsUseCase,
    private val notificationSchedulerPreferences: NotificationSchedulerPreferences
) {
    suspend fun execute() {
        Timber.d("count of periods: ${getMinCountOfPeriodsUseCase.execute()}")
        if (getMinCountOfPeriodsUseCase.execute() < Constants.COUNT_OF_PERIODS_FOR_ACTIVATION_NOTIFICATIONS) {
            Timber.d("Notification: canceled")
            everydayNotificationScheduler.cancel(Constants.EVERYDAY_NOTIFICATION_ID)
            return
        }
        if (notificationSchedulerPreferences.isScheduled()) {
            Timber.d("Notification is already scheduled")
            return
        }
        everydayNotificationScheduler.schedule(
            notificationId = Constants.EVERYDAY_NOTIFICATION_ID,
            hour = Constants.EVERYDAY_NOTIFICATION_HOUR,
            minute = Constants.EVERYDAY_NOTIFICATION_MINUTE
        )


        /**for test*/
        /*everydayNotificationScheduler.schedule(
            notificationId = Constants.EVERYDAY_NOTIFICATION_ID,
            hour = LocalTime.now().hour,
            minute = LocalTime.now().minute.plus(1)
        )*/
    }
}