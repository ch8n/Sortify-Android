package dev.ch8n.sortify.fragments.sortify

interface SortifyContact {

    interface View {
        fun isSortifyRequired(): Boolean
        fun startSortifyService()
        fun stopSortifyService()
        fun setSortifyRequired()
        fun setSortifyInProgress()
        fun setSortifyCompleted()
    }

    interface Controller {
        fun event(event: Event)
    }

    sealed class Event {
        object Init : Event()
        object StartSortify : Event()
        object Destroy : Event()
    }
}