package dev.ch8n.sortify.fragments.permission

import android.os.Bundle
import android.view.View
import dev.ch8n.sortify.R
import dev.ch8n.sortify.base.BaseFragment
import dev.ch8n.sortify.logEvents
import dev.ch8n.sortify.utils.STORAGE_READ_WRITE_RQCODE
import kotlinx.android.synthetic.main.fragment_permission.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.scope.bindScope
import org.koin.androidx.scope.lifecycleScope
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class PermissionFragment : BaseFragment(), PermissionContract.View,
    EasyPermissions.PermissionCallbacks {

    companion object {
        const val TAG = "PermissionFragment"
        const val PERMISSION_EXTRA = "permissions"

        fun newInstance(permissions: Array<String>): PermissionFragment {
            return PermissionFragment().apply {
                arguments = Bundle().apply {
                    putStringArray(PERMISSION_EXTRA, permissions)
                }
            }
        }
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_permission

    override val routeName: String
        get() = TAG

    override fun bindDiScope() {
        bindScope(getKoin().createScope<PermissionFragment>())
    }

    private val controller: PermissionContract.Controller by lifecycleScope.inject()

    override fun setup(view: View) {
        logEvents("launch_permission_fragment")
        controller.event(PermissionContract.Event.Init)
    }

    override fun attachInteractions() {
        view?.run {
            button_ask_permission.setOnClickListener {
                logEvents("click_permission_apply")
                controller.event(PermissionContract.Event.OnClickPermissionRequest)
            }
        }
    }

    override val permissions: Array<String>
        get() = arguments?.getStringArray(PERMISSION_EXTRA) ?: emptyArray()

    override fun askPermission() {
        EasyPermissions.requestPermissions(
            this,
            "Hi,Please provide the required permission to make the application functional for you!",
            STORAGE_READ_WRITE_RQCODE,
            *permissions
        )
    }

    override fun retryPermission() {
        logEvents("click_permission_retry")
        if (EasyPermissions.somePermissionPermanentlyDenied(this, permissions.toMutableList())) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        logEvents("click_permission_denied")
        controller.event(PermissionContract.Event.OnPermissionRejected)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        logEvents("on_permission_applied")
        controller.event(PermissionContract.Event.OnPermissionApplied)
    }
}
