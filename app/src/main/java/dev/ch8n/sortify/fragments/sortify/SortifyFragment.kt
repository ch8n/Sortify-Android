package dev.ch8n.sortify.fragments.sortify

import android.view.View
import dev.ch8n.sortify.R
import dev.ch8n.sortify.base.BaseFragment
import dev.ch8n.sortify.services.android.sort.SortService
import dev.ch8n.sortify.utils.SortifyUtil
import dev.ch8n.sortify.utils.setVisible
import dev.ch8n.sortify.utils.toast
import kotlinx.android.synthetic.main.fragment_sortify.view.*
import org.koin.android.ext.android.getKoin
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
        controller.event(SortifyContact.Event.Init(this))
        controller.event(SortifyContact.Event.CheckSortify)
    }

    override fun isSortifyRequired(): Boolean {
        val sortifyDir = SortifyUtil.getDownloadDirectory()
        return SortifyUtil.isSortifyRequired(sortifyDir)
    }

    override fun sortifiedRequired() {
        view?.run {
            image_sortify.setImageResource(R.drawable.ic_sort_required)
            button_sortify.text = "Let's Sortify"
            button_sortify.setVisible(true)
            button_sortify.setOnClickListener {
                controller.event(SortifyContact.Event.StartSortify)
            }
        }
    }

    override fun sortifiedAlready() {
        view?.run {
            image_sortify.setImageResource(R.drawable.ic_sort_already)
            button_sortify.setVisible(false)
        }
    }

    override fun sortifyInProgress() {
        view?.run {
            pulsator.start()
            image_sortify.setImageResource(R.drawable.ic_sort_inprogress)
            button_sortify.setVisible(false)
        }
    }

    override fun sortifyCompleted() {
        view?.run {
            pulsator.stop()
            image_sortify.setImageResource(R.drawable.ic_sort_completed)
            button_sortify.text = "it's sortified"
            button_sortify.setVisible(true)
            button_sortify.setOnClickListener {
                controller.event(SortifyContact.Event.CheckSortify)
            }
        }
    }


    override fun sortifyError(error: Exception) {
        view?.run {
            pulsator.stop()
            image_sortify.setImageResource(R.drawable.ic_error)
            text_message.text = error.message
            button_sortify.text = "retry"
            button_sortify.setVisible(true)
            button_sortify.setOnClickListener {
                controller.event(SortifyContact.Event.CheckSortify)
            }
        }
    }

    override fun startSortifyService() {
        SortService.startActionSort(requireContext())
    }

    override fun stopSortifyService() {
        SortService.stopService(requireContext())
    }

}