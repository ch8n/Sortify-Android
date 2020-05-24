package dev.ch8n.sortify.utils

import android.content.Context
import android.view.View
import android.widget.Toast


inline fun String.toToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

inline fun View.setVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}
