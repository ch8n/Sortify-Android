package dev.ch8n.sortify.services.android.notification

import android.R
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import dev.ch8n.sortify.utils.NotifyChannel
import dev.ch8n.sortify.utils.SortifyUtil
import pub.devrel.easypermissions.EasyPermissions
import java.util.concurrent.TimeUnit

class SortifyRequireRequest(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val isPermittedToSortify = EasyPermissions
            .hasPermissions(applicationContext, *SortifyUtil.appPermissions)

        if (isPermittedToSortify) {

            val isSortifyRequired = SortifyUtil.isSortifyRequired(
                SortifyUtil.getDownloadDirectory()
            )

            if (isSortifyRequired) {
                val notification = NotificationCompat.Builder(
                    applicationContext,
                    NotifyChannel.CHANNEL_ID_SORTIFY_PROCESSING
                ).setContentTitle("Let's Sortify!")
                    .setContentText("Hi, you have some unorganized files lets clean them up?")
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

        return Result.success()
    }

    companion object {

        fun setPeriodicNotification(appContext: Context) {
            val repeateInterval = 1L

            val sortifyPerodicCheck = PeriodicWorkRequestBuilder<SortifyRequireRequest>(
                repeateInterval,
                TimeUnit.DAYS
            ).build()

            WorkManager.getInstance(appContext).enqueue(sortifyPerodicCheck)
        }

        fun setOneTimeNotification(appContext: Context) {
            val sortifyOneTimeCheck = OneTimeWorkRequestBuilder<SortifyRequireRequest>()
                .build()

            WorkManager.getInstance(appContext).enqueue(sortifyOneTimeCheck)
        }

    }

}