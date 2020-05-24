package dev.ch8n.sortify

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
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
        initFirebaseRemoteConfig()

        startKoin {
            androidLogger()
            androidContext(this@SortifyApp)
            modules(AppModules)
        }
    }

    private fun initFirebaseRemoteConfig() {

        //todo fix remote config , make a json with version and active
        //todo keep a json with donation and user id , if donated dont show donate dialog
        //todo fix inApp messaging on app open
        //todo add admob ads in the app
        //todo google ways to montize app
        //todo add donation payment mode in app (in app purchase)
        //todo add pro feature ==> ability to select folder to sortify
        //todo todo add content strings using firebase remote
        //todo add silent push notification that starts check for chorne job for sortify notficiation
        //todo write test cases
        //todo use ab testing
        //todo use dynamic app to send user on my protfolio
        //todo add portfolio section in UI
        //todo explore debug view and stream view
        //todo add analytics events => track notification clicks and donation and protfolio
        //todo add open source notice
        //todo add privacy policy and terms and condition
        //todo add google login ==> use social id as unique identifier for donation tracking
        //todo add firestore database
        //todo send welcome email after sortify installed
        //todo add tranlation support

        val remoteConfig = Firebase.remoteConfig
        val defaults = hashMapOf<String, Any>()
        defaults.put("force_update", false)

        remoteConfig.setDefaultsAsync(defaults)
        remoteConfig.fetch(0)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    remoteConfig.activate()
                }
            }

    }
}