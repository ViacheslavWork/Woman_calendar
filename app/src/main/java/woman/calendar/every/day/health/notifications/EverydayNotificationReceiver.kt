package woman.calendar.every.day.health.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import timber.log.Timber

class EverydayNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val workManager = WorkManager.getInstance(context)
        val work = OneTimeWorkRequestBuilder<NotificationWorker>().build()
        workManager.enqueue(work)
        Timber.d("received")
        /*val intentService = Intent(context, EverydayNotificationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }*/
    }
}