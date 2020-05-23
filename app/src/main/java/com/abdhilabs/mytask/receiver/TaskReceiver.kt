package com.abdhilabs.mytask.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.abdhilabs.mytask.utils.DateTimeFormatter
import com.abdhilabs.mytask.utils.MORNING_NOTIFICATION
import com.abdhilabs.mytask.utils.sendNotification
import java.util.*

class TaskReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = ContextCompat.getSystemService(
            context!!,
            NotificationManager::class.java
        ) as NotificationManager

        val type = intent?.getStringExtra("type")
        val validateTime = intent?.getStringExtra("validateTime")

        if (intent != null) {
            if (getTimeNow() == validateTime) {
                if (type.equals(MORNING_NOTIFICATION)) {
                    notificationManager.sendNotification("You have task to do morning", context)
                } else {
                    Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getTimeNow(): String {
        val dateTimeMillis = System.currentTimeMillis()

//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = dateTimeMillis
        val morning = "07:00"
        val splitTime = morning.split(":")

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, splitTime[0].toInt())
            set(Calendar.MINUTE, splitTime[1].toInt())
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return DateTimeFormatter.timeOutput.format(calendar.time)
    }
}