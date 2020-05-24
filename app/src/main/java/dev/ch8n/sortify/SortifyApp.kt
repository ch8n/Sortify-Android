package dev.ch8n.sortify

import android.app.Application
import dev.ch8n.sortify.di.AppModules
import dev.ch8n.sortify.services.android.notification.SortifyRequireRequest
import dev.ch8n.sortify.utils.NotifyChannel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SortifyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        NotifyChannel.createNotifyChannels(this)

        SortifyRequireRequest.setPeriodicNotificationk(this)

        startKoin {
            androidLogger()
            androidContext(this@SortifyApp)
            modules(AppModules)
        }
    }
}