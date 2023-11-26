package com.example.testfirebasestackmobile.core.helpers

import android.content.Context
import android.view.View
import android.widget.EditText
import com.example.testfirebasestackmobile.R

class BlankBarrier {

    fun canItPass(context: Context, view: View, firstEdit: EditText, vararg otherEdits: EditText) : Boolean {
        val edits = mutableListOf<EditText>()
        with(edits) {
            add(firstEdit)
            addAll(otherEdits)

            forEach { edit ->
                if (edit.text.isBlank()) {
                    val ourSnackBar = OurSnackBar()
                    ourSnackBar.show(
                        context,
                        view,
                        R.string.snackbar_blank,
                        true
                    )
                    return false
                }
            }
        }
        return true
    }
}