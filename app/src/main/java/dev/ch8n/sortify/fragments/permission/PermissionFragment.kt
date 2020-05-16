package dev.ch8n.sortify.fragments.permission

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import dev.ch8n.sortify.R
import dev.ch8n.sortify.STORAGE_READ_WRITE_RQCODE
import dev.ch8n.sortify.base.BaseFragment
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class PermissionFragment : BaseFragment(), EasyPermissions.PermissionCallbacks {

    companion object {
        const val TAG = "PermissionFragment"
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_permission

    override fun setup(view: View) {


    }

    private fun requestPermissions() {
        //todo get permission from intent
        val permissions = arrayOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        EasyPermissions.requestPermissions(
            this,
            "Required to read write SD card",
            STORAGE_READ_WRITE_RQCODE,
            *permissions
        )
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
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        //todo goto home sortify

    }

}
