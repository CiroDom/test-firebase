package com.example.testfirebasestackmobile.core.utils

import android.content.Context
import android.view.View
import android.widget.EditText
import com.example.testfirebasestackmobile.R

class BlankBarrier {
    companion object {
        fun canItPass(snackbar: OurSnackbar, vararg edits: EditText) : Boolean {
            edits.forEach { edit ->
                    if (edit.text.isBlank()) {
                        snackbar.use(
                            R.string.snackbar_blank,
                            true
                        )
                        return false
                    }
                }
            return true
        }
    }
}