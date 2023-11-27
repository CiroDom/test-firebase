package com.example.testfirebasestackmobile.core.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar


class OurSnackBar {

    companion object {
        fun show(context: Context, view: View, getString: Int, warning: Boolean) {
            val snackBar = Snackbar.make(
                view,
                context.getString(getString),
                Snackbar.LENGTH_SHORT)

            val color = if (warning) Color.RED else Color.GREEN

            with(snackBar) {
                setBackgroundTint(color)
                show()
            }
        }
    }
}