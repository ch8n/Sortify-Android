package dev.ch8n.sortify

import androidx.fragment.app.FragmentActivity
import dev.ch8n.sortify.base.BaseNavigator
import dev.ch8n.sortify.fragments.permission.PermissionFragment
import dev.ch8n.sortify.fragments.sortify.SortifyFragment

class MainNavigator(activity: FragmentActivity) : BaseNavigator(activity), MainContract.Navigator {

    override fun toAskPermissionFragment(permission: Array<String>) {
        val permissionFragment = PermissionFragment.newInstance(permission)
        requireBaseActivity().supportFragmentManager.beginTransaction()
            .add(requireFragmentContainer().id, permissionFragment, PermissionFragment.TAG)
            .commit()
    }

    override fun toSorifyHomeFragment() {
        val sortifyFragment = SortifyFragment()
        requireBaseActivity().supportFragmentManager.beginTransaction()
            .add(requireFragmentContainer().id, sortifyFragment, SortifyFragment.TAG)
            .commit()
    }


}