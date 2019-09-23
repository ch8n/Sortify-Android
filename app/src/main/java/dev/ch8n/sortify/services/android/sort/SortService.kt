package dev.ch8n.sortify.services.android.sort

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Environment
import java.io.File.separator
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
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
            val path = download.listFiles()
            val isSortifyDirection = path
                .none { it.isDirectory && it.name.toLowerCase() == "sortify" }
            if (isSortifyDirection) {
                //case when its first time
                // create sortify folder
                //val directory = File(download.path + separator + "Sortify")
                //directory.mkdirs()
                // read files in download folder
                val extensions = DirectoryUtil.sorifyFolders()
                // according to files extensions create folders

                // move file from one directory to other
                // show user waring not to force quit may lead to data crouuption
                // have atleast 500Mb to 1gb space in storage
            } else {
                //case when sorify exist new content is there for sort
                // start a service that so even app exits work doesnt exits
                // read files in download folder
                // according to files extensions create folders
                // move file from one directory to other
                // show user waring not to force quit may lead to data crouuption
                // have atleast 500Mb to 1gb space in storage
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
