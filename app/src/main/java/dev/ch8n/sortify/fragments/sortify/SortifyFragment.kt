package dev.ch8n.sortify.fragments.sortify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import dev.ch8n.sortify.R
import dev.ch8n.sortify.base.BaseFragment
import dev.ch8n.sortify.services.android.sort.SortService
import dev.ch8n.sortify.toast

class SortifyFragment : BaseFragment() {

    companion object {
        const val TAG = "SortifyFragment"
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_sortify

    override fun setup(view: View) {


    }

    private fun startSortifyService() {
        "todo read write SD card".toast(requireContext())
        SortService.startActionSort(requireContext())
    }


}
