package dev.ch8n.sortify.base

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import dev.ch8n.sortify.R
import org.koin.ext.scope

abstract class BaseActivity : AppCompatActivity() {


    abstract val activityLayout: Int
    abstract fun attachDiScope()
    abstract fun setup()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityLayout)
        attachDiScope()
        setup()
    }

    fun getFragmentContainer(): ViewGroup? {
        return findViewById<ViewGroup>(R.id.container_fragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }


}