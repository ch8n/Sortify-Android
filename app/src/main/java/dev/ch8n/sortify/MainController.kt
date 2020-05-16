package dev.ch8n.sortify

import android.Manifest

class MainController(
    private val view: MainContract.View,
    private val navigator: MainContract.Navigator
) : MainContract.Controller {

    override fun event(event: MainContract.Event) =
        when (event) {
            is MainContract.Event.Init -> handleInit()
            is MainContract.Event.OnAskPermission -> onAskPermission(event.permission)
            is MainContract.Event.OnPermissionApplied -> onPermissionApplied()
        }

    private fun onPermissionApplied() {
        navigator.toSorifyHomeFragment()
    }

    private fun onAskPermission(permission: Array<String>) {
        navigator.toAskPermissionFragment(permission)
    }

    private fun handleInit() {
        val permissions = arrayOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        view.checkPermissions(permissions)
    }

}