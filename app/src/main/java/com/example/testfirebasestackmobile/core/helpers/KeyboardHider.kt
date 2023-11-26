package com.example.testfirebasestackmobile.core.helpers

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class KeyboardHider {

    fun use(context: Context, firstEdit: EditText, vararg otherEdits: EditText) {
        val edits = mutableListOf<EditText>()
        with(edits) {
            add(firstEdit)
            addAll(otherEdits)

            forEach { editText ->
                if (editText.isFocused) {
                    val inputMethodManager =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
                }
            }
        }
    }

}