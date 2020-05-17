package dev.ch8n.sortify

import dev.ch8n.sortify.base.BaseActivity
import org.koin.android.ext.android.getKoin
import org.koin.androidx.scope.bindScope
import org.koin.androidx.scope.lifecycleScope
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : BaseActivity(), MainContract.View {

    private val controller: MainContract.Controller by lifecycleScope.inject()

    override val activityLayout: Int
        get() = R.layout.activity_main

    override fun attachDiScope() {
        bindScope(getKoin().createScope<MainActivity>())
    }

    override fun setup() {
        controller.event(MainContract.Event.Init)
    }

    override fun checkPermissions(permission: Array<String>) {
        if (EasyPermissions.hasPermissions(this, *permission)) {
            controller.event(MainContract.Event.OnPermissionApplied)
        } else {
            controller.event(MainContract.Event.OnAskPermission(permission))
        }
    }

}

