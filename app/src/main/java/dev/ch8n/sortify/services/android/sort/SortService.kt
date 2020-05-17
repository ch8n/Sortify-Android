package dev.ch8n.sortify.services.android.sort

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Environment
import java.io.File.separator
import dev.ch8n.sortify.utils.SORTIFY_STRING
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
        val download =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        if (download.isDirectory) {

            // show forground notification
            // show broadcast event has started
            // get broadcast status and show progress on activity
            // use live data to push progress
            // when progress completes throw broadcast of complete
            // and update ui

            val sortifyDir = File(download.path.plus(separator).plus(SORTIFY_STRING))

            if (sortifyDir.exists()) {
                DirectoryUtil.createSortFolderAndMove(sortifyDir)
            } else {
                sortifyDir.mkdir()
                DirectoryUtil.createSortFolderAndMove(sortifyDir)
            }

        }

    }

    companion object {
        fun startActionSort(context: Context) {
            val intent = Intent(context.applicationContext, SortService::class.java).apply {
                action = ACTION_SORT
            }
            context.startService(intent)
        }
    }
}
