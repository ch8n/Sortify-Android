package dev.ch8n.sortify.fragments.sortify

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dev.ch8n.sortify.utils.Result
import dev.ch8n.sortify.utils.SortifyUtil

class SortifyController(val view: SortifyContact.View) : SortifyContact.Controller {

    override fun event(event: SortifyContact.Event) = when (event) {
        is SortifyContact.Event.Init -> onInit(event.lifecycleOwner)
        SortifyContact.Event.CheckSortify -> onCheckSortify()
        SortifyContact.Event.StartSortify -> onStartSortify()
    }

    private fun onInit(lifecycleOwner: LifecycleOwner) {
        SortifyUtil.observeSortifyService.observe(lifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    if (result.value) {
                        view.stopSortifyService()
                        view.sortifyCompleted()
                        SortifyUtil._sortifyService.value = null
                    } else {
                        view.stopSortifyService()
                        view.sortifyError(Exception("Oops.."))
                    }
                }
                is Result.Error -> {
                    view.stopSortifyService()
                    view.sortifyError(result.error)
                }
            }
        })
    }

    private fun onStartSortify() {
        view.sortifyInProgress()
        view.startSortifyService()
    }

    private fun onCheckSortify() {
        val isSortRequired = view.isSortifyRequired()
        if (isSortRequired) {
            view.sortifiedRequired()
        } else {
            view.sortifiedAlready()
        }
    }
}