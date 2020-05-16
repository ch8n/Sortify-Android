package dev.ch8n.sortify

import android.Manifest
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import dev.ch8n.sortify.fragments.permission.PermissionFragment
import dev.ch8n.sortify.fragments.sortify.SortifyFragment
import dev.ch8n.sortify.services.android.sort.SortService
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.bindScope
import org.koin.ext.scope
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File


class MainActivity : AppCompatActivity(), MainContract.View {

    private val controller: MainContract.Controller by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindScope(getKoin().createScope<MainActivity>())
        controller.event(MainContract.Event.Init)
    }

    override fun checkPermissions(permission: Array<String>) {
        if (EasyPermissions.hasPermissions(this, *permission)) {
            controller.event(MainContract.Event.OnPermissionApplied)
        } else {
            controller.event(MainContract.Event.OnAskPermission(permission))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }

}

