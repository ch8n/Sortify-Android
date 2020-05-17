package dev.ch8n.sortify.fragments.sortify

interface SortifyContact {

    interface View {
        fun isSortifyRequired(): Boolean
        fun startSortifyService()
        fun stopSortifyService()
    }

    interface Controller {
        fun event(state: State)
    }

    sealed class State {
        object Init : State()
    }
}