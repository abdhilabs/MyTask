package com.abdhilabs.mytask.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.abdhilabs.mytask.utils.setupNotification

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.setupNotification()
        } else {
            context?.setupNotification()
        }
    }
}