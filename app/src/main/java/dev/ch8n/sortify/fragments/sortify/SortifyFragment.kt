package dev.ch8n.sortify.fragments.sortify

import android.view.View

import dev.ch8n.sortify.R
import dev.ch8n.sortify.base.BaseFragment
import dev.ch8n.sortify.services.android.sort.SortService
import dev.ch8n.sortify.utils.toast

class SortifyFragment : BaseFragment() {

    companion object {
        const val TAG = "SortifyFragment"
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_sortify

    override val routeName: String
        get() = TAG

    override fun bindDiScope() {

    }

    override fun setup(view: View) {
        
    }

    private fun startSortifyService() {
        "todo read write SD card".toast(requireContext())
        SortService.startActionSort(requireContext())
    }

}
