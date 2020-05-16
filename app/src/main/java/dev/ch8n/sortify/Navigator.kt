package dev.ch8n.sortify

import androidx.fragment.app.FragmentActivity
import dev.ch8n.sortify.fragments.permission.PermissionFragment
import dev.ch8n.sortify.fragments.sortify.SortifyFragment
import kotlinx.android.synthetic.main.activity_main.*

class Navigator(private val activity: FragmentActivity) : MainContract.Navigator {

    override fun toAskPermissionFragment(permission: Array<String>) {
        with(activity) {
            val permissionFragment = PermissionFragment()
            supportFragmentManager.beginTransaction()
                .add(container_fragment.id, permissionFragment, PermissionFragment.TAG)
                .commit()
        }
    }

    override fun toSorifyHomeFragment() {
        with(activity) {
            val sortifyFragment = SortifyFragment()
            supportFragmentManager.beginTransaction()
                .add(container_fragment.id, sortifyFragment, SortifyFragment.TAG)
                .commit()
        }
    }

}