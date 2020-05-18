package dev.ch8n.sortify.fragments.sortify

import android.view.View

import dev.ch8n.sortify.R
import dev.ch8n.sortify.base.BaseFragment
import dev.ch8n.sortify.services.android.sort.SortService
import dev.ch8n.sortify.utils.DirectoryUtil
import dev.ch8n.sortify.utils.setVisible
import dev.ch8n.sortify.utils.toast
import kotlinx.android.synthetic.main.fragment_sortify.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.bindScope
import org.koin.androidx.scope.lifecycleScope

class SortifyFragment : BaseFragment(), SortifyContact.View {

    companion object {
        const val TAG = "SortifyFragment"
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_sortify

    override val routeName: String
        get() = TAG

    override fun bindDiScope() {
        bindScope(getKoin().createScope<SortifyFragment>())
    }

    val controller: SortifyContact.Controller by lifecycleScope.inject()

    override fun setup(view: View) {
        controller.event(SortifyContact.Event.Init)
    }

    override fun isSortifyRequired(): Boolean {
        val sortifyDir = DirectoryUtil.getSortifyDirectory()
        return DirectoryUtil.isSortifyRequired(sortifyDir)
    }

    override fun setSortifyRequired() {
        view?.run {
            image_sortify.setImageResource(R.drawable.ic_cluttered_content)
            button_sortify.setVisible(true)
            button_sortify.setOnClickListener {
                controller.event(SortifyContact.Event.StartSortify)
            }
        }
    }

    override fun setSortifyInProgress() {
        view?.run {
            image_sortify.setImageResource(R.drawable.ic_sort_inprogress)
            button_sortify.setVisible(false)
        }
    }

    override fun setSortifyCompleted() {
        view?.run {
            image_sortify.setImageResource(R.drawable.ic_already_organized)
            button_sortify.setVisible(false)
        }
    }

    override fun startSortifyService() {
        "todo read write SD card".toast(requireContext())
        SortService.startActionSort(requireContext())
    }

    override fun stopSortifyService() {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        controller.event(SortifyContact.Event.Destroy)
    }
}
