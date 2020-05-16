package dev.ch8n.sortify.di

import dev.ch8n.sortify.MainActivity
import dev.ch8n.sortify.MainContract
import dev.ch8n.sortify.MainController
import dev.ch8n.sortify.Navigator
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module


val mainModule = module {
    scope<MainActivity> {
        scoped<MainContract.View> { get<MainActivity>() }
        scoped<MainContract.Navigator> { Navigator(get<MainActivity>()) }
        scoped<MainContract.Controller> { MainController(get(), get()) }
    }
}

val AppModules = listOf<Module>(mainModule)
