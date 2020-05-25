package dev.ch8n.sortify

import android.app.Application
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dev.ch8n.sortify.base.BaseActivity
import dev.ch8n.sortify.base.BaseFragment
import dev.ch8n.sortify.di.AppModules
import dev.ch8n.sortify.services.android.notification.SortifyRequireRequest
import dev.ch8n.sortify.utils.NotifyChannel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class SortifyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        logEvents("app_launch")
        NotifyChannel.createNotifyChannels(this)
        SortifyRequireRequest.setPeriodicNotification(this)
        initFirebaseRemoteConfig()
        if (BuildConfig.DEBUG) {
            logFirebaseToken()
        }

        startKoin {
            androidLogger()
            androidContext(this@SortifyApp)
            modules(AppModules)
        }
    }

    private fun logFirebaseToken() {

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.e("firebase", "token:\n ${it.result.token}")
                Log.e("firebase", "instanceId:\n ${it.result.id}")
            } else {
                Log.e("firebase", "token: Error")
            }
        }
    }

    private fun initFirebaseRemoteConfig() {

        //todo release - 1.0 add silent push notification that starts check for chorne job for sortify notficiation

        //todo release - 1.0 add analytics events => track notification clicks and donation and protfolio

        //todo release - 1.0 add open source notice

        //todo release - 1.0 add privacy policy and terms and condition

        //todo add portfolio section in UI

        //todo release - 1.0 add admob ads in the app

        //todo release - 1.0 add donation payment mode in app (in app purchase)

        //todo release - 1.0 fix inApp messaging on app open

        //todo todo add content strings using firebase remote

        //todo release - 1.0 add google login ==> use social id as unique identifier for donation tracking
        //todo release - 1.0 add firestore database
        //todo release - 1.0 keep a json with donation and user id , if donated dont show donate dialog

        //todo google ways to montize app

        //todo add pro feature ==> ability to select folder to sortify

        //todo write test cases

        //todo release - 1.0 use ab testing
        //todo release - 1.0 use dynamic app to send user on my protfolio

        //todo explore debug view and stream view

        //todo send welcome email after sortify installed
        //todo add tranlation support

        val remoteConfig = Firebase.remoteConfig
        val remoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
            .setFetchTimeoutInSeconds(0)
            .build()

        remoteConfig.setConfigSettingsAsync(remoteConfigSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote)

        remoteConfig.fetchAndActivate()
    }
}

fun SortifyApp.logEvents(event: String, bundle: Bundle = Bundle.EMPTY) {
    FirebaseAnalytics.getInstance(applicationContext).logEvent(event, bundle)
}

fun BaseActivity.logEvents(event: String, bundle: Bundle = Bundle.EMPTY) {
    FirebaseAnalytics.getInstance(applicationContext).logEvent(event, bundle)
}

fun BaseFragment.logEvents(event: String, bundle: Bundle = Bundle.EMPTY) {
    FirebaseAnalytics.getInstance(requireActivity().applicationContext).logEvent(event, bundle)
}

