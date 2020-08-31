package com.abdhilabs.mytask.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.abdhilabs.mytask.data.repository.TaskRepository
import com.abdhilabs.mytask.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class TaskReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: TaskRepository

    private var job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent?) {
//        DaggerAppComponent.builder()
//            .applicationContext(context)
//            .build()
//            .inject(this)

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        val notifyId = intent?.getStringExtra("notifyId")?.toInt()
        val type = intent?.getStringExtra("type")
        val validateTime = intent?.getStringExtra("validateTime")

        if (intent != null) {
            if (getTimeNow() == validateTime) {
                when {
                    type.equals(DAILY_NOTIFICATION) -> {
                        coroutineScope.launch {
                            repository.getTask().collect { task ->
                                if (task.isNotEmpty()) {
                                    for (i in task.indices) {
                                        notificationManager.createNotification(
                                            notifyId,
                                            "You have task : ${task[i].title}",
                                            "The deadline is : ${task[i].deadline}",
                                            context
                                        )
                                    }
                                } else {
                                    notificationManager.createNotification(
                                        notifyId,
                                        "Empty",
                                        "You not have any task todo",
                                        context
                                    )
                                }
                            }
                        }
                    }
                    type.equals(FCM_NOTIFICATION) -> {
                        notificationManager.createNotificationFcm("Fcm Notification", context)
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
