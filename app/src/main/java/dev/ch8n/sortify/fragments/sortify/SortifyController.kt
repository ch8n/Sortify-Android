package dev.ch8n.sortify.fragments.sortify

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SortifyController(val view: SortifyContact.View) : SortifyContact.Controller, CoroutineScope {

    override fun event(event: SortifyContact.Event) = when (event) {
        SortifyContact.Event.Init -> onInit()
        SortifyContact.Event.StartSortify -> onStartSortify()
        SortifyContact.Event.Destroy -> onDestory()
    }

    private fun onDestory() {
        coroutineContext.cancel()
    }

    private fun onStartSortify() {
        launch(Dispatchers.Main) {
            view.sortifyInProgress()
            delay(5000)
            view.sortifyCompleted()
        }
    }

    private fun onInit() {
        val isSortRequired = view.isSortifyRequired()
        if (isSortRequired) {
            view.sortifiedRequired()
        } else {
            view.sortifyCompleted()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job()

}