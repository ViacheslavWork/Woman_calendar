package com.period.tracker.natural.cycles.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import timber.log.Timber
import com.period.tracker.natural.cycles.preferences.NotificationSchedulerPreferences
import java.util.*

const val RUN_DAILY = (24 * 60 * 60 * 1000).toLong()

class EverydayNotificationScheduler(
    private val context: Context,
    private val notificationSchedulerPreferences: NotificationSchedulerPreferences
) {
    //TODO
    fun schedule(notificationId: Int, hour: Int, minute: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, EverydayNotificationReceiver::class.java)
//        intent.putExtra(ALARM_ID, alarmId)
        val notificationPendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)


        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            RUN_DAILY,
            notificationPendingIntent
        )
        /*val alarmClockInfo =
            AlarmManager.AlarmClockInfo(calendar.timeInMillis, notificationPendingIntent)
        alarmManager.setAlarmClock(alarmClockInfo, notificationPendingIntent)*/
        notificationSchedulerPreferences.setIsScheduled(isScheduled = true)
        Timber.d("scheduled at $hour:$minute")
    }

    fun cancel(notificationId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, EverydayNotificationReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(alarmPendingIntent)
        notificationSchedulerPreferences.setIsScheduled(isScheduled = false)
        Timber.d("canceled notification: $notificationId")
    }
}