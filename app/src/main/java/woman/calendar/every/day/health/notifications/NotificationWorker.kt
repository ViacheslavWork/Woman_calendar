package woman.calendar.every.day.health.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.usecase.GetDailyNotificationDataUseCase


class NotificationWorker(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params), KoinComponent {
    private val getDailyNotificationDataUseCase: GetDailyNotificationDataUseCase by inject()
    override suspend fun doWork(): Result {
        getDailyNotificationDataUseCase.execute(LocalDate.now())?.let {
            EverydayNotification(applicationContext).show(
                date = LocalDate.now(),
                content = it.notificationText
            )
            return Result.success()
        }
        return Result.failure()
    }
}