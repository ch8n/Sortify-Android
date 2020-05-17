package dev.ch8n.sortify.di

import dev.ch8n.sortify.MainActivity
import dev.ch8n.sortify.MainContract
import dev.ch8n.sortify.MainController
import dev.ch8n.sortify.MainNavigator
import dev.ch8n.sortify.fragments.permission.PermissionContract
import dev.ch8n.sortify.fragments.permission.PermissionController
import dev.ch8n.sortify.fragments.permission.PermissionFragment
import dev.ch8n.sortify.fragments.permission.PermissionNavigator
import org.koin.core.module.Module
import org.koin.dsl.module


val androidMainModule = module {
    scope<MainActivity> {
        scoped<MainContract.View> { get<MainActivity>() }
        scoped<MainContract.Navigator> { MainNavigator(get<MainActivity>()) }
        scoped<MainContract.Controller> { MainController(get(), get()) }
    }
}

val androidPermissionModule = module {
    scope<PermissionFragment> {
        scoped<PermissionContract.View> { get<PermissionFragment>() }
        scoped<PermissionContract.Navigator> { PermissionNavigator(get<PermissionFragment>()) }
        scoped<PermissionContract.Controller> { PermissionController(get(), get()) }
    }
}

val AppModules = listOf<Module>(
    androidMainModule,
    androidPermissionModule
)
