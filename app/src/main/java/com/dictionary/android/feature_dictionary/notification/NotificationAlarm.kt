package com.dictionary.android.feature_dictionary.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.util.*

class NotificationAlarm(
    private val context: Context
) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(word: String) {
        val intent = Intent(context, NotificationAlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", word)
        }

        val triggerTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (triggerTime.timeInMillis < System.currentTimeMillis()){
            triggerTime.add(Calendar.DAY_OF_YEAR, 1)
        }
        Toast.makeText(context,"Alarm Scheduled at ${triggerTime.time}",Toast.LENGTH_LONG).show()
        Log.d("NextTriggerTime","Alarm scheduled at ${triggerTime.time}")

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTime.timeInMillis,
            AlarmManager.INTERVAL_HOUR,
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}