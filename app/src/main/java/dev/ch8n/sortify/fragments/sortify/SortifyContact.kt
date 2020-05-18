package dev.ch8n.sortify.fragments.sortify

import androidx.lifecycle.LifecycleOwner

interface SortifyContact {

    interface View {
        fun startSortifyService()
        fun stopSortifyService()


        fun isSortifyRequired(): Boolean
        fun sortifiedRequired()
        fun sortifiedAlready()
        fun sortifyInProgress()
        fun sortifyCompleted()
        fun sortifyError(error: Exception)
    }

    interface Controller {
        fun event(event: Event)
    }

    sealed class Event {
        data class Init(val lifecycleOwner: LifecycleOwner) : Event()
        object CheckSortify : Event()
        object StartSortify : Event()
    }
}