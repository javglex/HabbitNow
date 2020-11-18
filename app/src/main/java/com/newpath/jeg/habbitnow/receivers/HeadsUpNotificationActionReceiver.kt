package com.newpath.jeg.habbitnow.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.newpath.jeg.habbitnow.MainActivity
import com.newpath.jeg.habbitnow.constants.NotificationConstants
import com.newpath.jeg.habbitnow.services.HeadsUpNotificationService


class HeadsUpNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null && intent.extras != null) {
            val action = intent.getStringExtra(NotificationConstants.ActionKey.EXTRA_ALARM_ID_KEY)

            action?.let { performClickAction(context, action) }

            // Close the notification after the click action is performed.
            val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
            context.sendBroadcast(it)
            context.stopService(Intent(context, HeadsUpNotificationService::class.java))
        }
    }

    private fun performClickAction(context: Context, action: String) {

        if (action.equals(NotificationConstants.ActionType.ACCEPT_ACTION_KEY) ) {

            var openIntent: Intent?  = null

            try {
                openIntent = Intent(context, MainActivity::class.java)
                openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(openIntent)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace();
            }

        } else if (action.equals(NotificationConstants.ActionType.DEFER_ACTION_KEY)) {

            context.stopService(Intent(context, HeadsUpNotificationService::class.java))
            var it: Intent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
            context.sendBroadcast(it)

        }
    }
}