package woman.calendar.every.day.health.domain.usecase.notification

import org.threeten.bp.LocalTime
import woman.calendar.every.day.health.domain.usecase.periods.GetCountOfPeriodsUseCase
import woman.calendar.every.day.health.notifications.EverydayNotificationScheduler
import woman.calendar.every.day.health.utils.Constants
import woman.calendar.every.day.health.utils.NotificationSchedulerPreferences

class OnOffEverydayNotificationUseCase(
    private val everydayNotificationScheduler: EverydayNotificationScheduler,
    private val getCountOfPeriodsUseCase: GetCountOfPeriodsUseCase,
    private val notificationSchedulerPreferences: NotificationSchedulerPreferences
) {
    suspend fun execute() {
        if (getCountOfPeriodsUseCase.execute() < Constants.COUNT_OF_PERIODS_FOR_ACTIVATION_NOTIFICATIONS) {
            everydayNotificationScheduler.cancel(Constants.EVERYDAY_NOTIFICATION_ID)
            return
        }
        /*if (!notificationSchedulerPreferences.isScheduled()) {
            everydayNotificationScheduler.schedule(
                notificationId = Constants.EVERYDAY_NOTIFICATION_ID,
                hour = Constants.EVERYDAY_NOTIFICATION_HOUR,
                minute = Constants.EVERYDAY_NOTIFICATION_MINUTE
            )
        }*/
        everydayNotificationScheduler.schedule(
            notificationId = Constants.EVERYDAY_NOTIFICATION_ID,
            hour = LocalTime.now().hour,
            minute = LocalTime.now().minute.plus(1)
        )
    }
}