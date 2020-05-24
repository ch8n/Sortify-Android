package dev.ch8n.sortify.fragments.sortify

import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dev.ch8n.sortify.R
import dev.ch8n.sortify.base.BaseFragment
import dev.ch8n.sortify.services.android.sort.SortService
import dev.ch8n.sortify.utils.SortifyUtil
import dev.ch8n.sortify.utils.setVisible
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

    override fun checkForceUpdate() {
        val remoteConfig = Firebase.remoteConfig
        val isForceUpdate = remoteConfig.getBoolean("force_update")
        if (isForceUpdate) {
            getForceUpdateDialog().show()
        }
    }

    fun getForceUpdateDialog(): AlertDialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Urgent Update")
            .setMessage("Please update the application to keep it functional!")
            .setCancelable(false)
            .setPositiveButton("Sure") { dialog, id ->
                requireActivity().finish()
            }.create()
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