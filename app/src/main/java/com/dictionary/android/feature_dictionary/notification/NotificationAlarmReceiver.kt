package com.dictionary.android.feature_dictionary.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.dictionary.android.MainActivity
import com.dictionary.android.R

class NotificationAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        sendNotification(message,context!!)
    }

   private fun sendNotification(description: String,context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val pendingIntent: PendingIntent = createPendingIntent(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_check_circle_outline_24)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()


        notificationManager.notify(NOTIFICATION_ID,builder)
        //NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder)
    }

    private fun createPendingIntent(appContext: Context): PendingIntent {
        val intent = Intent(appContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        var flags = PendingIntent.FLAG_UPDATE_CURRENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags = flags or PendingIntent.FLAG_IMMUTABLE
        }

        return PendingIntent.getActivity(
            appContext,
            REQUEST_CODE,
            intent,
            flags
        )
    }


    companion object {
        const val CHANNEL_ID = "WOTD_NOTIFICATION"
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_CHANNEL_NAME = "Word Of The Day Notifications"
        const val NOTIFICATION_CHANNEL_DESCRIPTION = "Shows notifications every day a one time"
        const val NOTIFICATION_TITLE = "Word Of The Day"
        const val REQUEST_CODE = 0
    }
}