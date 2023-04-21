package com.dictionary.android

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.dictionary.android.feature_dictionary.notification.NotificationAlarm
import com.dictionary.android.feature_dictionary.notification.NotificationAlarmReceiver.Companion.CHANNEL_ID
import com.dictionary.android.feature_dictionary.notification.NotificationAlarmReceiver.Companion.NOTIFICATION_CHANNEL_DESCRIPTION
import com.dictionary.android.feature_dictionary.notification.NotificationAlarmReceiver.Companion.NOTIFICATION_CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DictionaryApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = NOTIFICATION_CHANNEL_DESCRIPTION
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val alarmManager = NotificationAlarm(this)
        alarmManager.schedule("CAR")
    }
}