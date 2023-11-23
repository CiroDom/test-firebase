package com.example.testfirebasestackmobile.ui.activities

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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

                fun showSnackBar(getString: Int, warning: Boolean) {
                    val color = if (warning) Color.RED else Color.GREEN

                    val snackBar = Snackbar.make(
                        button,
                        getString(getString),
                        Snackbar.LENGTH_SHORT)

                    with(snackBar) {
                        setBackgroundTint(color)
                        show()
                    }
                }

                if (email.isBlank() || passw.isBlank()) {
                    showSnackBar(R.string.snackbar_blank, true)
                } else {
                    auth.createUserWithEmailAndPassword(email.toString(), passw.toString())
                        .addOnCompleteListener { signIn ->
                            if (signIn.isSuccessful ) {
                                showSnackBar(R.string.snackbar_sucess_sign_in, false)

                                with(binding) {
                                    signinEditEmail.text.clear()
                                    signinEditPassw.text.clear()
                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                            val errorMsgInt = when (exception) {
                                is FirebaseAuthWeakPasswordException -> R.string.snackbar_fail_weakpassw_sign_in
                                is FirebaseAuthInvalidCredentialsException -> R.string.snackbar_fail_invalid_credential
                                is FirebaseAuthUserCollisionException -> R.string.snackbar_fail_collision_sign_in
                                is FirebaseNetworkException -> R.string.snackbar_fail_conection
                                else -> R.string.snackbar_fail_generic
                            }

                            showSnackBar(errorMsgInt, true)
                        }
                }
            }

            signinLinlayParent.setOnClickListener {
                fun hideKeyboard(editText: View) {
                    val outRect = Rect()

                    editText.getGlobalVisibleRect(outRect)

                    if (
                        !outRect.contains(editText.x.toInt(), editText.y.toInt())
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
                } else if (editPassw.isFocused) {
                    hideKeyboard(editPassw)
                }
            }
        }
    }
}