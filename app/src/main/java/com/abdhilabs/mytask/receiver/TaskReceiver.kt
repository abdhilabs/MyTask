package com.abdhilabs.mytask.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.abdhilabs.mytask.service.TaskService
import com.abdhilabs.mytask.utils.*
import java.util.*

class TaskReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationManager = ContextCompat.getSystemService(
            context!!,
            NotificationManager::class.java
        ) as NotificationManager
        val type = intent?.getStringExtra("type")
        val validateTime = intent?.getStringExtra("validateTime")

        val notifyId = intent?.getStringExtra("notifyId")?.toInt()
        val title = intent?.getStringExtra("title")
        val deadline = intent?.getStringExtra("deadline")

        if (intent != null) {
            if (getTimeNow() == validateTime) {
                when {
                    type.equals(DAILY_NOTIFICATION) -> {
                        if (title == "Empty") {
                            notificationManager.sendNotification(notifyId, title, deadline, context)
                        } else {
                            notificationManager.sendNotification(
                                notifyId,
                                "You have task : $title",
                                "The deadline is : $deadline",
                                context
                            )
                        }
                    }
                    type.equals(FCM_NOTIFICATION) -> {
                        notificationManager.sendNotificationFcm("Fcm Notification", context)
                    }
                    type.equals(UPDATE_NOTIFICATION) -> {
                        val serviceIntent = Intent(context, TaskService::class.java)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            context.startForegroundService(serviceIntent)
                        } else {
                            context.startService(serviceIntent)
                        }
                    }
                }
            }
        }
    }
}

private fun getTimeNow(): String {
    val dateTimeMillis = System.currentTimeMillis()

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = dateTimeMillis

    return DateTimeFormatter.timeOutput.format(calendar.time)
}
