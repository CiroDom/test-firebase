package com.example.testfirebasestackmobile.ui.form_login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testfirebasestackmobile.R
import com.example.testfirebasestackmobile.databinding.ActivityFormLoginBinding
import com.example.testfirebasestackmobile.databinding.ActivityMainViewBinding
import com.example.testfirebasestackmobile.ui.forgot_passw.ForgotPasswActivity
import com.example.testfirebasestackmobile.ui.form_sign_in.FormSignInActivity
import com.example.testfirebasestackmobile.ui.main_view.MainViewActivity
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class FormLoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormLoginBinding.inflate(layoutInflater)
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            loginButtonNext.setOnClickListener{ button ->
                val email = binding.loginEditEmail.text
                val passw = binding.loginEditPassw.text

                fun showErrorSnackBar(getString: Int) {
                    val snackBar = Snackbar.make(button, getString(getString), Snackbar.LENGTH_SHORT)

                    with(snackBar) {
                        setBackgroundTint(Color.RED)
                        show()
                    }
                }

                if (email.isBlank() || passw.isBlank()) {
                    showErrorSnackBar(R.string.snackbar_blank)

                    return@setOnClickListener
                }

                auth
                    .signInWithEmailAndPassword(email.toString(), passw.toString())
                    .addOnCompleteListener { authentication ->
                        if (authentication.isSuccessful) {
                            val intent = Intent(
                                this@FormLoginActivity,
                                MainViewActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }.addOnFailureListener { exception ->
                        val errorMsgInt = when (exception) {
                            is FirebaseNetworkException -> R.string.snackbar_fail_conection
                            is FirebaseAuthInvalidCredentialsException -> R.string.snackbar_fail_invalid_credential
                            else -> R.string.snackbar_fail_generic_auth
                        }
                        showErrorSnackBar(errorMsgInt)
                    }
            }

            loginTxtNoAcc.setOnClickListener {
                goToAnotherView(ActivityMainViewBinding::class.java)
            }

            loginTxtForgetPassw.setOnClickListener {
                goToAnotherView(ForgotPasswActivity::class.java)
            }

            loginButtonSigninGoogle.setOnClickListener {

            }
        }

    }

    override fun onStart() {
        super.onStart()

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            goToAnotherView(ActivityMainViewBinding::class.java)
        }
    }

    private fun goToAnotherView(destiny: Class<*>) {
        val intent = Intent(this@FormLoginActivity, destiny)
        startActivity(intent)
        finish()
    }

    private fun googleSigninOptions() {
        val googleSigninOpt = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        googleSigninOpt.requestIdToken()
    }
}