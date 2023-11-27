package com.example.testfirebasestackmobile.core.helpers

import android.content.Context
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class KeyboardHider {

    fun use(context: Context, edits: List<EditText>) {
        fun hideKeyboard(editText: EditText) {
            val rect = Rect()

            editText.getGlobalVisibleRect(rect)

            if (
                !rect.contains(editText.x.toInt(), editText.y.toInt())
            ) {
                val inputMethodManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
            }
        }

        for (editText in edits) {
            if (editText.isFocused) {
                hideKeyboard(editText)
                break
            }
        }
    }
}