package dev.ch8n.sortify.fragments.sortify

import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dev.ch8n.sortify.BuildConfig
import dev.ch8n.sortify.R
import dev.ch8n.sortify.base.BaseFragment
import dev.ch8n.sortify.logEvents
import dev.ch8n.sortify.services.android.sort.SortService
import dev.ch8n.sortify.utils.SortifyUtil
import dev.ch8n.sortify.utils.setVisible
import dev.ch8n.sortify.utils.toToast
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
        logEvents("launch_sortify_fragment")
        controller.event(SortifyContact.Event.Init(this))
        controller.event(SortifyContact.Event.CheckSortify)
    }

    override fun checkForceUpdate() {
        val remoteConfig = Firebase.remoteConfig
        val appVersionCode = remoteConfig.getLong("active_version_code")
        val isActiveVersionCode = appVersionCode == BuildConfig.VERSION_CODE.toLong()
        if (!isActiveVersionCode) {
            forceUpdateDialog().show()
        }
    }

    private fun forceUpdateDialog(): AlertDialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Urgent Update")
            .setMessage("Please update the application to keep it functional!")
            .setCancelable(false)
            .setNegativeButton("Close") { dialog, id ->
                logEvents("click_sortify_version_update_dismissed")
                dialog.dismiss()
                requireActivity().finish()
            }.setPositiveButton("Sure") { dialog, id ->
                logEvents("click_sortify_version_update_download")
                dialog.dismiss()
                "todo open playstore link".toToast(requireActivity())
            }.create()
    }

    override fun isSortifyRequired(): Boolean {
        val sortifyDir = SortifyUtil.getDownloadDirectory()
        return SortifyUtil.isSortifyRequired(sortifyDir)
    }

    override fun sortifiedRequired() {
        view?.run {
            logEvents("launch_sortify_required_fragment")
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
            logEvents("launch_sortify_already_fragment")
            image_sortify.setImageResource(R.drawable.ic_sort_already)
            button_sortify.setVisible(false)
            // todo search to achive this  ==> open downlaod folder
//            button_sortify.text = "Downloads"
//            button_sortify.setOnClickListener {
//                val sortifyUri = Uri.parse(SortifyUtil.getDownloadDirectory().path)
//                val intent = Intent(Intent.ACTION_GET_CONTENT)
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setDataAndType(sortifyUri, "*/*");
//                startActivity(Intent.createChooser(intent, "Open folder"))
//            }
        }
    }

    override fun sortifyInProgress() {
        view?.run {
            logEvents("launch_sortify_processing_fragment")
            pulsator.start()
            image_sortify.setImageResource(R.drawable.ic_sort_inprogress)
            button_sortify.setVisible(false)
        }
    }

    override fun sortifyCompleted() {
        view?.run {
            logEvents("launch_sortify_completed_fragment")
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
            logEvents("launch_sortify_error_fragment")
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
