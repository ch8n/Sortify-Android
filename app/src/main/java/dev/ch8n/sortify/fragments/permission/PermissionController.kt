package dev.ch8n.sortify.fragments.permission

class PermissionController(
    private val view: PermissionContract.View,
    private val navigator: PermissionContract.Navigator
) : PermissionContract.Controller {

    override fun event(event: PermissionContract.Event) = when (event) {
        is PermissionContract.Event.Init -> onInit()
        is PermissionContract.Event.OnClickPermissionRequest -> onClickPermissionRequest()
        is PermissionContract.Event.OnPermissionApplied -> onPermissionApplied()
        is PermissionContract.Event.OnPermissionRejected -> onPermissionRejected()
    }

    private fun onPermissionRejected() {
        view.retryPermission()
    }

    private fun onPermissionApplied() {
        navigator.toSortifyHomeFragment()
    }

    private fun onClickPermissionRequest() {
        view.askPermission()
    }

    private fun onInit() {
        view.attachInteractions()
    }
}