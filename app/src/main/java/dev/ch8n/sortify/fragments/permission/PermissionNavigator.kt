package dev.ch8n.sortify.fragments.permission

import androidx.fragment.app.Fragment
import dev.ch8n.sortify.base.BaseNavigator
import dev.ch8n.sortify.fragments.sortify.SortifyFragment

class PermissionNavigator(
    private val fragment: Fragment
) : BaseNavigator(fragment),
    PermissionContract.Navigator {

    override fun toSortifyHomeFragment() {
        requireBaseActivity()
            .supportFragmentManager
            .beginTransaction()
            .remove(fragment)
            .add(requireFragmentContainer().id, SortifyFragment(), SortifyFragment.TAG)
            .commit()
    }
}
