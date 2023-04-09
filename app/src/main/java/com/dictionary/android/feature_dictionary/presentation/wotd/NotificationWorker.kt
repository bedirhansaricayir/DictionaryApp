package com.dictionary.android.feature_dictionary.presentation.wotd

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.dictionary.android.MainActivity
import com.dictionary.android.R
import java.util.concurrent.TimeUnit

class NotificationWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
): CoroutineWorker(context,workerParams) {
    override suspend fun doWork(): Result {
        Log.d("workManager","Geldi birşeyler.")
        sendNotification("Obcussion")


        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val repeatInterval = 1L // saat cinsinden
        val flexInterval = 1L // saat cinsinden

        val nextTime = System.currentTimeMillis() + repeatInterval * 60* 60* 1000


        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            repeatInterval, TimeUnit.HOURS,
            flexInterval,TimeUnit.HOURS
        ).setConstraints(constraints)
            .setInitialDelay(nextTime - System.currentTimeMillis(),TimeUnit.MILLISECONDS)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
        return Result.success()
    }
    private fun sendNotification(description: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = NOTIFICATION_CHANNEL_DESCRIPTION

            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent = createPendingIntent(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()


        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder)
    }

    fun createPendingIntent(appContext: Context): PendingIntent {
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

        const val TAG = "NotificationWorker"

        fun start(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(20, TimeUnit.MINUTES).build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                TAG,
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )
        }

        fun stop(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(TAG)
        }
    }


}