package com.abdhilabs.mytask.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.abdhilabs.mytask.App
import com.abdhilabs.mytask.R
import com.abdhilabs.mytask.receiver.TaskReceiver
import java.util.*

private const val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun setupNotification(app: Application) {
    val notifyIntent = Intent(app, TaskReceiver::class.java)
    val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    App.pref.isChecked = true

    val morning = "07:00"
    val splitTime = morning.split(":")

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, splitTime[0].toInt())
        set(Calendar.MINUTE, splitTime[1].toInt())
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    //Add Message here
    notifyIntent.also {
        it.putExtra("validateTime", DateTimeFormatter.timeOutput.format(calendar.time))
        it.putExtra("type", MORNING_NOTIFICATION)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        app,
        10 + REQUEST_CODE,
        notifyIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )

//        alarmManager.setAlarmClock(
//            AlarmManager.AlarmClockInfo(
//                calendar.timeInMillis,
//                pendingIntent
//            ), pendingIntent
//        )
}

fun cancelNotification(app: Application) {
    val notifyIntent = Intent(app, TaskReceiver::class.java)
    val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    App.pref.isChecked = false
    val pendingIntent = PendingIntent.getBroadcast(
        app,
        10 + REQUEST_CODE,
        notifyIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    alarmManager.cancel(pendingIntent)
}

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val channelId = applicationContext.resources.getString(R.string.channel_id)
    val channelName = applicationContext.resources.getString(R.string.channel_name)

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.resources.getString(R.string.channel_id)
    )
        .setSmallIcon(R.drawable.ic_document)
        .setContentTitle("Check your task now")
        .setContentText(messageBody)

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
    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotification() {
    cancelAll()
}