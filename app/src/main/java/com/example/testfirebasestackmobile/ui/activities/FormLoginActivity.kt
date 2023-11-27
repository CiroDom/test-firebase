package com.example.testfirebasestackmobile.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import com.example.testfirebasestackmobile.R
import com.example.testfirebasestackmobile.core.singletons.Auth.auth
import com.example.testfirebasestackmobile.databinding.ActivityFormLoginBinding
import com.example.testfirebasestackmobile.ui.dialog.LoadingDialog
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

                auth.signInWithEmailAndPassword(email.toString(), passw.toString())
                    .addOnCompleteListener { authentication ->
                        if (authentication.isSuccessful) {
                            goToAnotherView(MainViewActivity::class.java)
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
                goToAnotherView(FormSignInActivity::class.java)
            }

            loginTxtForgetPassw.setOnClickListener {
                goToAnotherView(ForgotPasswActivity::class.java)
            }

            loginButtonSigninGoogle.setOnClickListener {
                loginGoogle()
            }

            loginLinlayParent.setOnClickListener {
                Log.i("Login", "chegou")
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

                if (editEmail.isFocused) {
                    hideKeyboard(editEmail)
                }
                else if (editPassw.isFocused) {
                    hideKeyboard(editPassw)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            goToAnotherView(MainViewActivity::class.java)
        }
    }

    private fun goToAnotherView(destiny: Class<*>) {
        dialog.showAlertDialog()

        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismissAlertDialog()

            val intent = Intent(this@FormLoginActivity, destiny)
            startActivity(intent)
        }, 500)
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

        auth.signInWithCredential(credential).addOnCompleteListener { authentication ->
            if (authentication.isSuccessful) {
                goToAnotherView(MainViewActivity::class.java)
            }
        }
    }
}