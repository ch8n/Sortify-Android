package dev.ch8n.sortify

import android.app.Application
import dev.ch8n.sortify.utils.NotifyChannel

class SortifyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        NotifyChannel.createNotifyChannels(this)
    }
}