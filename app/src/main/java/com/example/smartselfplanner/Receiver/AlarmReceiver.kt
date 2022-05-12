package com.example.smartselfplanner.Receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.smartselfplanner.R
import com.example.smartselfplanner.utils.sendNotification

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
       // Toast.makeText(context, "Your Task is done!", Toast.LENGTH_SHORT).show()

        // TODO: Step 1.9 add call to sendNotification
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(
            context.getText(R.string.task_Done_Message).toString(),
            context
        )
    }

}