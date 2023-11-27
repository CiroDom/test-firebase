package com.example.testfirebasestackmobile.ui.activities

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.testfirebasestackmobile.core.utils.KeyboardHider
import com.example.testfirebasestackmobile.databinding.ActivityFormSignInBinding
import com.example.testfirebasestackmobile.databinding.FragPersonBinding

class FormSignInActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormSignInBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val parentLay = binding.singInLinlayParent

        parentLay.setOnClickListener {
            KeyboardHider.hideKeyboard(this)
        }
    }
}