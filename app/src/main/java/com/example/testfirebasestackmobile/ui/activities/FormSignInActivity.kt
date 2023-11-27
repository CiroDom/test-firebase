package com.example.testfirebasestackmobile.ui.activities

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.testfirebasestackmobile.core.helpers.KeyboardHider
import com.example.testfirebasestackmobile.databinding.ActivityFormSignInBinding
import com.example.testfirebasestackmobile.databinding.FragAccountBinding
import com.example.testfirebasestackmobile.databinding.FragPersonBinding

class FormSignInActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormSignInBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val parentLay = binding.singInLinlayParent

        val personFragLay = FragPersonBinding.inflate(layoutInflater)
        val editFName = personFragLay.fragpersonEditFirstname
        val editSName = personFragLay.fragpersonEditSecondname

        parentLay.setOnClickListener {
            fun hideKeyboard(editText: EditText) {
                val rect = Rect()

                editText.getGlobalVisibleRect(rect)

                if (
                    !rect.contains(editText.x.toInt(), editText.y.toInt())
                ) {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
                }
            }

            if (editFName.isFocused) {
                hideKeyboard(editFName)
            }
            else if (editSName.isFocused) {
                hideKeyboard(editSName)
            }
        }
    }
}