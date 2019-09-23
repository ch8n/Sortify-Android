package dev.ch8n.sortify.services.android.sort

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File.separator
import dev.ch8n.sortify.SORTIFY_STRING
import dev.ch8n.sortify.utils.DirectoryUtil
import java.io.File


private const val ACTION_SORT = "dev.ch8n.sortify.services.android.sort.action.SORT"

class SortService : IntentService("SortService") {

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_SORT -> handleSortEvent()
        }
    }

    private fun handleSortEvent() {
        val download = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        if (download.isDirectory) {
            val sortifyDir = File(download.path + separator + SORTIFY_STRING)

            if (sortifyDir.exists()) {
                DirectoryUtil.createSortFolderAndMove(sortifyDir)
            } else {
                sortifyDir.mkdir()
                DirectoryUtil.createSortFolderAndMove(sortifyDir)
            }

        }

    }

    companion object {

        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         * @see IntentService
         */

        @JvmStatic
        fun startActionSort(context: Context) {
            val intent = Intent(context, SortService::class.java).apply {
                action = ACTION_SORT
            }
            context.startService(intent)
        }
    }
}
