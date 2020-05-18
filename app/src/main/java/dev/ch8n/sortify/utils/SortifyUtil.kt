package dev.ch8n.sortify.utils

import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.File


object SortifyUtil {

    val _sortifyService = MutableLiveData<Result<Boolean, Exception>>()

    val observeSortifyService: LiveData<Result<Boolean, Exception>> = _sortifyService

    fun getDownloadDirectory(): File = requireNotNull(
        Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        )
    )

    fun getSortifyDirectory(insideFile: File): File {
        return File(insideFile.path.plus(File.separator).plus(SORTIFY_STRING))
    }

    fun isSortifyRequired(sortifyDir: File): Boolean {
        return (sortifyDir.listFiles() ?: emptyArray()).any { !it.isDirectory }
    }

    fun getSortedFolderName(file: File): String {
        return when (file.extension) {
            "aif", "cda", "mid", "midi", "mp3", "mpa", "ogg", "wav", "wma", "wpl" -> "audio"
            "7z", "arj", "deb", "pkg", "rar", "rpm", "tar.gz", "gz", "z", "zip" -> "compressed"
            "bin", "dmg", "iso", "toast", "vcd" -> "disk-image"
            "csv", "dat", "db", "dbf", "log", "mdb", "sav", "sql", "tar", "xml" -> "data-db"
            "apk", "bat", "cgi", "pl", "com", "exe", "gadget", "jar", "py", "wsf" -> "executable"
            "fnt", "fon", "otf", "ttf" -> "fonts"
            "ai", "bmp", "gif", "ico", "jpeg", "jpg", "png", "ps", "psd", "svg", "tif", "tiff" -> "image-type"
            "asp", "aspx", "cer", "cfm", "css", "htm", "html", "js", "jsp", "part", "php", "rss", "xhtml" -> "web-type"
            "key", "odp", "pps", "ppt", "pptx" -> "presentation"
            "c", "class", "cpp", "cs", "h", "java", "sh", "swift", "vb", "kt" -> "programming"
            "bak", "cab", "cfg", "cpl", "cur", "dll", "dmp", "drv", "icns", "ini", "lnk", "msi", "sys", "tmp" -> "system-type"
            "3g2", "3gp", "avi", "flv", "h264", "m4v", "mkv", "mov", "mp4", "mpg", "mpeg", "rm", "swf", "vob", "wmv" -> "video-type"
            "doc", "docx", "odt", "pdf", "rtf", "tex", "txt", "wks", "wps", "wpd" -> "text-type"
            "ods", "xlr", "xls", "xlsx" -> "spreadsheet"
            else -> "misc"
        }
    }

    fun sortify(targetDir: File, sortifyDir: File) {
        val directoryItems = targetDir.listFiles() ?: emptyArray<File>()
        directoryItems.filter { !it.isDirectory }.forEach { currentFile ->
            val folderName = getSortedFolderName(currentFile)
            val sortedFolder = File(sortifyDir.path + File.separator + folderName)
            if (!sortedFolder.exists()) {
                sortedFolder.mkdir()
            }
            currentFile.renameTo(
                File(
                    sortedFolder.path.plus(File.separator).plus(currentFile.name)
                )
            )
        }
    }
}