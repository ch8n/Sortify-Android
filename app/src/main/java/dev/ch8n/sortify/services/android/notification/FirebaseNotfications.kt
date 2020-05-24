package dev.ch8n.sortify.services.android.notification

import android.R
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dev.ch8n.sortify.utils.NotifyChannel

class FirebaseNotifications : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val firebaseNotif = remoteMessage.notification
        if (firebaseNotif != null) {
            showNotification(firebaseNotif)
        }

    }

    private fun showNotification(firebaseNotif: RemoteMessage.Notification) {
        val notification = NotificationCompat.Builder(
            applicationContext,
            NotifyChannel.CHANNEL_ID_SORTIFY_PROCESSING
        ).setContentTitle(firebaseNotif.title)
            .setContentText(firebaseNotif.body)
            .setSmallIcon(R.drawable.ic_menu_sort_alphabetically)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(applicationContext).let {
            it.notify(1, notification)
        }
    }
}