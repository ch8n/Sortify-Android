package dev.ch8n.sortify.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat

object NotifyChannel {
    const val CHANNEL_ID_SORTIFY_PROCESSING = "dev.ch8n.sortify.processing"

    fun createNotifyChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val service = NotificationChannel(
                CHANNEL_ID_SORTIFY_PROCESSING,
                "Sortifying...",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notifyManager = NotificationManagerCompat
                .from(context.applicationContext)

            notifyManager.createNotificationChannel(service)
        }
    }
}
