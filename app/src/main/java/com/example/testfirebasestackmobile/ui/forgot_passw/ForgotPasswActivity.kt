package com.example.testfirebasestackmobile.ui.forgot_passw

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testfirebasestackmobile.R
import com.example.testfirebasestackmobile.databinding.ActivityForgotPasswBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class ForgotPasswActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityForgotPasswBinding.inflate(layoutInflater)
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.forgotButtonNext.setOnClickListener { button ->
            fun showSnackBar(getString: Int, error: Boolean) {
                val snackBar = Snackbar.make(button, getString(getString), Snackbar.LENGTH_SHORT)
                val color = if (error) Color.RED else Color.GREEN

                with(snackBar) {
                    setBackgroundTint(color)
                    show()
                }
            }

            val email = binding.forgotEditEmail.text

            if (email.isBlank()) {
                showSnackBar(R.string.snackbar_blank, true)

                return@setOnClickListener
            }

            auth
                .sendPasswordResetEmail(email.toString())
                .addOnCompleteListener { redfPassw ->
                    if (redfPassw.isSuccessful) {
                        showSnackBar(R.string.snackbar_check_email, false)
                    }
                }
                .addOnFailureListener { exception ->
                    val msgErrorInt = when(exception) {
                        is FirebaseAuthInvalidCredentialsException -> R.string.snackbar_fail_invalid_credential
                        is FirebaseNetworkException -> R.string.snackbar_fail_conection
                        else -> R.string.snackbar_fail_generic_forgot
                    }

                    showSnackBar(msgErrorInt, true)
                }
        }
    }
}