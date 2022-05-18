package com.example.smartselfplanner.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.smartselfplanner.DailyToDo.DailyTodoFragment
import com.example.smartselfplanner.MainActivity
import com.example.smartselfplanner.R


// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0


fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    // Create the content intent for the notification, which launches
    // this activity
    // create intent (User clicked the notification -> send to the activitiy related to that)
    //val contentIntent = Intent(applicationContext, MainActivity::class.java)

    //create PendingIntent
    val pendingIntent = NavDeepLinkBuilder(applicationContext)
        .setGraph(R.navigation.navigation)
        .setDestination(R.id.dailyTodoFragment)
        //.setArguments(args)
        .createPendingIntent()

/*    //create PendingIntent
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )*/

    //  get an instance of NotificationCompat.Builder
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.dailyTask_channel_id)
    )
        // set title, text and icon to builder
        .setSmallIcon(R.drawable.ic_check)
        .setContentTitle(applicationContext
            .getString(R.string.notification_title))
        .setContentText(messageBody)

        //  set content intent
        .setContentIntent(pendingIntent)
            //When user click the notification, it will be gone
        .setAutoCancel(true)

    // Deliver the notification
    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}