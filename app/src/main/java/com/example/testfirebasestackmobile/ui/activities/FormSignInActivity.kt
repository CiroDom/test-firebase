package com.example.testfirebasestackmobile.ui.activities

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.testfirebasestackmobile.R
import com.example.testfirebasestackmobile.databinding.ActivityFormSignInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormSignInActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormSignInBinding.inflate(layoutInflater)
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            signinButtonNext.setOnClickListener { button ->
                val email = binding.signinEditEmail.text
                val passw = binding.signinEditPassw.text


                }
            }

            signinLinlayParent.setOnClickListener {
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

                val editEmail = binding.signinEditEmail
                val editPassw = binding.signinEditPassw

                if (editEmail.isFocused) {
                    hideKeyboard(editEmail)
                }
                else if (editPassw.isFocused) {
                    hideKeyboard(editPassw)
                }
            }
        }
    }
}