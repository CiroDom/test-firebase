package com.example.testfirebasestackmobile.ui.form_login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import com.example.testfirebasestackmobile.R
import com.example.testfirebasestackmobile.core.singletons.Auth
import com.example.testfirebasestackmobile.core.utils.ActvChanger
import com.example.testfirebasestackmobile.core.utils.KeyboardHider
import com.example.testfirebasestackmobile.core.utils.OurSnackbar
import com.example.testfirebasestackmobile.databinding.ActivityFormLoginBinding
import com.example.testfirebasestackmobile.ui.main_view.MainViewActivity
import com.example.testfirebasestackmobile.ui.dialog.LoadingDialog
import com.example.testfirebasestackmobile.ui.forgot_passw.ForgotPasswActivity
import com.example.testfirebasestackmobile.ui.form_sign_in.FormSignInActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.GoogleAuthProvider

class FormLoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormLoginBinding.inflate(layoutInflater)
    }

    private lateinit var loginGoogleClient: GoogleSignInClient

    private val googleLoginResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == requestedOrientation) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.getResult(ApiException::class.java)

            userGoogleAuthenticate(account.idToken!!)
        }

    }

    private val dialog = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        googleSigninOptions()

        with(binding) {
            val editEmail = binding.loginEditEmail
            val editPassw = binding.loginEditPassw

            loginButtonNext.setOnClickListener { button ->
                val email = editEmail.text
                val passw = editPassw.text

                fun showErrorSnackBar(getString: Int) {
                    val snackBar =
                        Snackbar.make(button, getString(getString), Snackbar.LENGTH_SHORT)

                    with(snackBar) {
                        setBackgroundTint(Color.RED)
                        show()
                    }
                }

                if (email.isBlank() || passw.isBlank()) {
                    showErrorSnackBar(R.string.snackbar_blank)

                    return@setOnClickListener
                }

                val snackbar = OurSnackbar(this@FormLoginActivity, button)
                Auth.signInWithEmailAndPassword(email.toString(), passw.toString(), this@FormLoginActivity, snackbar)
            }

            loginTxtNoAcc.setOnClickListener {
                ActvChanger.use(this@FormLoginActivity, FormSignInActivity::class.java, false)
            }

            loginTxtForgetPassw.setOnClickListener {
                ActvChanger.use(this@FormLoginActivity, ForgotPasswActivity::class.java, false)
            }

            loginButtonSigninGoogle.setOnClickListener {
                loginGoogle()
            }

            loginLinlayParent.setOnClickListener {
                KeyboardHider.use(this@FormLoginActivity)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            ActvChanger.use(this, MainViewActivity::class.java, true)
        }
    }

    private fun googleSigninOptions() {
        val googleSigninOpt =
            GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.oauth_client))
            .requestEmail()
            .build()

        loginGoogleClient = GoogleSignIn.getClient(this, googleSigninOpt)
    }

    private fun loginGoogle() {
        val intent = loginGoogleClient.signInIntent

        googleLoginResult.launch(intent)
    }

    private fun userGoogleAuthenticate(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        Auth.signInWithCredential(credential, this)
    }
}