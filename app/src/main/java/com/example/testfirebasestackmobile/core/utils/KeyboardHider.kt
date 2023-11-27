package com.example.testfirebasestackmobile.core.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

class KeyboardHider {

    companion object {
        fun use(actv: AppCompatActivity) {
            val view = actv.currentFocus
            if (view != null) {
                val inputMethodManager = actv.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

}