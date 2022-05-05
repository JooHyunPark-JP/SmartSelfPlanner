package com.example.smartselfplanner.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.smartselfplanner.R

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // TODO: Step 1.10 [Optional] remove toast
       //Toast.makeText(context, context.getText(R.string.eggs_ready), Toast.LENGTH_SHORT).show()
        Toast.makeText(context, "Your Task is done!", Toast.LENGTH_SHORT).show()
        // TODO: Step 1.9 add call to sendNotification

    }

}