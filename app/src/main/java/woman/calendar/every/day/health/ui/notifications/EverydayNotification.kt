package woman.calendar.every.day.health.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.ui.MainActivity
import woman.calendar.every.day.health.utils.Constants

class EverydayNotification(val context: Context, val content: String) {
    fun start() {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val notificationBuilder = getNotificationBuilder()
        notificationManager.notify(
            Constants.EVERYDAY_NOTIFICATION_ID,
            notificationBuilder.build()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            Constants.NOTIFICATION_EVERYDAY_CHANNEL_ID,
            Constants.NOTIFICATION_EVERYDAY_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun getNotificationBuilder() = NotificationCompat.Builder(
        context,
        Constants.NOTIFICATION_EVERYDAY_CHANNEL_ID
    )
        .setAutoCancel(true)
        .setOngoing(false)
        //TODO
        .setSmallIcon(R.drawable.ic_alcohol)
        .setContentTitle(content)
        .setContentIntent(getMainActivityPendingIntent())

    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        context,
        0,
        Intent(context, MainActivity::class.java)
            .apply { action = MainActivity.ACTION_SHOW_NOTIFICATION_SCREEN },
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
}