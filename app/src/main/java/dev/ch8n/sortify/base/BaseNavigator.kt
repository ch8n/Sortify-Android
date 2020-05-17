package dev.ch8n.sortify.base

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

abstract class BaseNavigator(private val baseActivity: FragmentActivity) {

    private var baseFragment: BaseFragment? = null

    constructor(fragment: Fragment) : this(fragment.requireActivity()) {
        baseFragment = (fragment as? BaseFragment) ?: kotlin.run {
            throw IllegalStateException("Navigator activity must have fragment container with id container_fragment")
        }
    }

    fun requireBaseFragment(): BaseFragment {
        return baseFragment ?: kotlin.run {
            throw IllegalStateException("BaseFragment not initalized")
        }
    }

    fun requireFragmentContainer(): ViewGroup {
        return requireBaseActivity().getFragmentContainer() ?: kotlin.run {
            throw IllegalStateException("Navigator activity must have fragment container with id container_fragment")
        }
    }

    fun requireBaseActivity(): BaseActivity {
        return (baseActivity as? BaseActivity) ?: kotlin.run {
            throw IllegalStateException("Host activity must have extend BaseActivity")
        }
    }

}