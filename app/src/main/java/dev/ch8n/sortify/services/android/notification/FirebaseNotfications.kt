package dev.ch8n.sortify.services.android.notification

import android.R
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dev.ch8n.sortify.utils.NotifyChannel
import dev.ch8n.sortify.utils.toToast

class FirebaseNotifications : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        when {
            remoteMessage.data.isNotEmpty() && remoteMessage.notification == null -> handleSilentPush(
                remoteMessage.data
            )
            remoteMessage.notification != null -> handlePushNotification(
                requireNotNull(
                    remoteMessage.notification
                )
            )
        }

    }

    private fun handlePushNotification(notification: RemoteMessage.Notification) {
        showNotification(notification)
    }

    private fun handleSilentPush(data: Map<String, String>) {
        Log.e("firebase", "silent notification : ${data.keys.joinToString(",")}")
        if (data.isNotEmpty()) {
            val isCheckRequired = data.get("checkStortify")?.toBoolean() ?: false
            if (isCheckRequired) {
                SortifyRequireRequest.setOneTimeNotification(applicationContext)
            }
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

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("firebase", "token : $token")
    }
}