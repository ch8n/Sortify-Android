package dev.ch8n.sortify

import android.content.Context
import android.widget.Toast

fun String.toast(context: Context) {
    Toast.makeText(context,this, Toast.LENGTH_SHORT).show()
}
