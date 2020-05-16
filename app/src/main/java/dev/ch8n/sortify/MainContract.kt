package dev.ch8n.sortify

interface MainContract {

    interface View {
        fun checkPermissions(permission: Array<String>)
    }

    interface Controller {
        fun event(event: Event)
    }

    interface Navigator {
        fun toAskPermissionFragment(permission: Array<String>)
        fun toSorifyHomeFragment()
    }

    sealed class Event {
        object Init : Event()
        data class OnAskPermission(val permission: Array<String>) : Event()
        object OnPermissionApplied : Event()
    }

}