package com.abdhilabs.mytask.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.receiver.TaskReceiver
import com.abdhilabs.mytask.ui.activities.MainActivity
import java.util.*

private const val FCM_NOTIFICATION_ID = 1
private const val REQUEST_CODE = 0

fun Context.setupNotification() {
    toast(resources.getString(R.string.message_switcher_on))
//    pref.isChecked = true
    val alarmTime = listOf(
        "07:00",
        "19:00"
    )
    var index = 0
    alarmTime.forEach {
        val alarm = DateTimeFormatter.getTimeTimestamp(it)
        setupAlarm(index + 1, DAILY_NOTIFICATION, alarm)
        index++
    }
}

private fun Context.setupAlarm(
    requestCode: Int,
    type: String,
    calendar: Calendar
) {
    val notifyIntent = Intent(applicationContext, TaskReceiver::class.java)
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

    //Add Message here
    notifyIntent.also {
        it.putExtra("notifyId", "$requestCode")
        it.putExtra("validateTime", DateTimeFormatter.timeOutput.format(calendar.time))
        it.putExtra("type", type)
    }
    val pendingIntent = PendingIntent.getBroadcast(
        applicationContext,
        10 + requestCode,
        notifyIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    // For testing
//        val selectedInterval = 1_000L * 5
//    val triggerTime = SystemClock.elapsedRealtime() + selectedInterval
//
//    alarmManager.setExactAndAllowWhileIdle(
//        AlarmManager.ELAPSED_REALTIME_WAKEUP,
//        triggerTime,
//        pendingIntent
//    )

//     Repeating Alarm
    alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )

    // Intermediate Alarm
//        alarmManager.setAlarmClock(
//            AlarmManager.AlarmClockInfo(
//                calendar.timeInMillis,
//                pendingIntent
//            ), pendingIntent
//        )
}

fun Activity.cancelNotification() {
    toast(resources.getString(R.string.message_switcher_off))
    val notifyIntent = Intent(this, TaskReceiver::class.java)
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

//    pref.isChecked = false
    val pendingIntent = PendingIntent.getBroadcast(
        applicationContext,
        10 + REQUEST_CODE,
        notifyIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    alarmManager.cancel(pendingIntent)
    pendingIntent.cancel()
}

fun NotificationManager.createNotification(
    notifyId: Int?,
    title: String?,
    deadline: String?,
    applicationContext: Context
) {
    val channelId = applicationContext.resources.getString(R.string.channel_id)
    val channelName = applicationContext.resources.getString(R.string.channel_name)
    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        REQUEST_CODE + notifyId!!,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.resources.getString(R.string.channel_id)
    )
        .setSmallIcon(R.drawable.ic_document)
        .setContentTitle(title)
        .setContentText(deadline)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)

    // Create Notification Channel
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.also {
            it.enableLights(true)
            it.lightColor = Color.RED
            it.enableVibration(true)
            it.description = "Time to check your task"
        }
        val notificationManager = applicationContext.getSystemService(
            NotificationManager::class.java
        )
        notificationManager?.createNotificationChannel(notificationChannel)
    }
    notify(notifyId, builder.build())
}

fun NotificationManager.createNotificationFcm(
    message: String,
    applicationContext: Context
) {
    val channelId = applicationContext.resources.getString(R.string.channel_fcm_id)
    val channelName = applicationContext.resources.getString(R.string.channel_fcm_name)
    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        REQUEST_CODE,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.resources.getString(R.string.channel_fcm_id)
    )
        .setSmallIcon(R.drawable.ic_document)
        .setContentTitle("Firebase Cloud Messaging")
        .setContentText(message)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)

    // Create Notification Channel
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.also {
            it.enableLights(true)
            it.lightColor = Color.RED
            it.enableVibration(true)
            it.description = "Time to check your task"
        }
        val notificationManager = applicationContext.getSystemService(
            NotificationManager::class.java
        )
        notificationManager?.createNotificationChannel(notificationChannel)
    }
    notify(FCM_NOTIFICATION_ID, builder.build())
}