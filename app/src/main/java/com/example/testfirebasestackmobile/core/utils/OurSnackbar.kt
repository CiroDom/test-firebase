package com.example.testfirebasestackmobile.core.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar


class OurSnackbar(
    private val context: Context,
    private val view: View,
) {

    fun use(msg: Int, warning: Boolean) {
        val color = if (warning) Color.RED else Color.GREEN

        val snackBar = Snackbar.make(
            view,
            context.getString(msg),
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(color)

        snackBar.show()
    }
}