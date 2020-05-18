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
            view.setSortifyInProgress()
            delay(2000)
            view.setSortifyCompleted()
        }
    }

    private fun onInit() {
        val isSortRequired = view.isSortifyRequired()
        if (isSortRequired) {
            view.setSortifyRequired()
        } else {
            view.setSortifyCompleted()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job()

}