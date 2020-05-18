package dev.ch8n.sortify.fragments.sortify

interface SortifyContact {

    interface View {
        fun startSortifyService()
        fun stopSortifyService()


        fun isSortifyRequired(): Boolean
        fun sortifiedRequired()
        fun sortifiedAlready()
        fun sortifyInProgress()
        fun sortifyCompleted()
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