package dev.ch8n.sortify.fragments.permission

import dev.ch8n.sortify.MainContract

interface PermissionContract {

    interface View {
        val permissions: Array<String>
        fun attachInteractions()
        fun askPermission()
        fun retryPermission()
    }

    interface Controller {
        fun event(event: Event)
    }

    interface Navigator {
        fun toSortifyHomeFragment()
    }

    sealed class Event {
        object Init : Event()
        object OnClickPermissionRequest : Event()
        object OnPermissionApplied : Event()
        object OnPermissionRejected : Event()
    }
}