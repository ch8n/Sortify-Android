package dev.ch8n.sortify.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.ext.scope

abstract class BaseFragment : Fragment() {

    abstract val fragmentLayout: Int

    abstract val routeName: String

    abstract fun bindDiScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindDiScope()
        return inflater.inflate(fragmentLayout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
    }


    abstract fun setup(view: View)

    override fun onDestroyView() {
        super.onDestroyView()
        scope.close()
    }

}