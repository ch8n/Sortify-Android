package dev.ch8n.sortify.services.android.sort

import android.R
import android.app.IntentService
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import dev.ch8n.sortify.utils.NotifyChannel
import dev.ch8n.sortify.utils.Result
import dev.ch8n.sortify.utils.SortifyUtil
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.*

private const val ACTION_SORT = "dev.ch8n.sortify.services.android.sort.action.SORT"

class SortService : IntentService("SortService"), CoroutineScope {

    override fun onHandleIntent(intent: Intent?) {

        val notification =
            NotificationCompat.Builder(baseContext, NotifyChannel.CHANNEL_ID_SORTIFY_PROCESSING)
                .setContentTitle("Sortifying...")
                .setContentText("Please don't terminate the app while files are been organized")
                .setSmallIcon(R.drawable.ic_menu_sort_alphabetically)
                .setAutoCancel(false)
                .build()

        startForeground(1, notification)

        when (intent?.action) {
            ACTION_SORT -> handleSortEvent()
        }
    }

    private fun handleSortEvent() {

        launch(Dispatchers.IO) {
            val download = SortifyUtil.getDownloadDirectory()
            if (download.isDirectory) {
                val sortifyDir = SortifyUtil.getSortifyDirectory(download)
                if (!sortifyDir.exists()) {
                    sortifyDir.mkdir()
                }
                val result = try {
                    SortifyUtil.sortify(download, sortifyDir)
                    Result.build { true }
                } catch (e: Exception) {
                    Result.build { throw e }
                }
                delay(2000)
                SortifyUtil._sortifyService.postValue(result)
            }
        }
    }

    companion object {
        fun startActionSort(context: Context) {
            val serviceIntent = Intent(context, SortService::class.java)
                .apply {
                    action = ACTION_SORT
                }
            ContextCompat.startForegroundService(context, serviceIntent)
        }

        fun stopService(context: Context) {
            val serviceIntent = Intent(context, SortService::class.java)
            context.stopService(serviceIntent)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job() + CoroutineExceptionHandler { coroutineContext, throwable ->
            val result = Result.build { throw throwable }
            SortifyUtil._sortifyService.postValue(result)
        }
}
