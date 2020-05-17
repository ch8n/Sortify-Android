package dev.ch8n.sortify.base

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity

abstract class BaseNavigator(private val baseActivity: FragmentActivity) {

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