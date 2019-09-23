package dev.ch8n.sortify.utils

import android.os.Environment
import java.io.File

object DirectoryUtil {

    fun sorifyFolders(): Array<String> {
        val downloadDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        getFilesFrom(downloadDir)
        val folders = mutableSetOf<String>()
        extensions.forEach { extension ->
            when (extension) {
                "aif", "cda", "mid", "midi", "mp3", "mpa", "ogg", "wav", "wma", "wpl" -> folders.add("audio")
                "7z","arj", "deb","pkg","rar","rpm","tar.gz","gz","z","zip" -> folders.add("compressed")
                "bin","dmg", "iso","toast","vcd" -> folders.add("disk-image")
                "csv","dat", "db","dbf","log","mdb","sav","sql","tar","xml" -> folders.add("data-db")
                "apk","bat","cgi","pl","com","exe","gadget","jar","py","wsf" -> folders.add("executable")
                "fnt","fon","otf","ttf" -> folders.add("fonts")
                "ai","bmp","gif","ico","jpeg","jpg","png","ps","psd","svg","tif","tiff" -> folders.add("image-type")
                "asp","aspx","cer","cfm","css","htm","html","js","jsp","part","php","rss","xhtml" -> folders.add("web-type")
                "key","odp","pps","ppt","pptx" -> folders.add("presentation")
                "c","class","cpp","cs","h","java","sh","swift","vb","kt" -> folders.add("programming")
                "bak","cab","cfg","cpl","cur","dll","dmp","drv","icns","ini","lnk","msi","sys","tmp" -> folders.add("system-type")
                "3g2","3gp","avi","flv","h264","m4v","mkv","mov","mp4","mpg","mpeg","rm","swf","vob","wmv" -> folders.add("video-type")
                "doc","docx","odt","pdf","rtf","tex","txt","wks","wps","wpd" -> folders.add("text-type")
                "ods","xlr","xls","xlsx" -> folders.add("spreadsheet")
            }
        }
        return folders.toTypedArray()
    }


    private val extensions = mutableSetOf<String>()
    private fun getFilesFrom(dir: File) {
        dir.listFiles().forEach { file ->
            if (file.isDirectory) {
                getFilesFrom(file)
            } else {
                extensions.add(file.extension)
            }
        }
    }
}