package com.example.testfirebasestackmobile.core.singletons

import android.app.Activity
import android.util.Log
import com.example.testfirebasestackmobile.R
import com.example.testfirebasestackmobile.core.utils.ActvChanger
import com.example.testfirebasestackmobile.core.utils.ConstRepo
import com.example.testfirebasestackmobile.core.utils.OurSnackbar
import com.example.testfirebasestackmobile.ui.main_view.MainViewActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser

object Auth {

    private val auth = FirebaseAuth.getInstance()

    val user = auth.currentUser

    fun createUserWithEmailAndPassword(email: String, passw: String, personData: Map<String, String?>, snackbar: OurSnackbar) {
        auth.createUserWithEmailAndPassword(email, passw)
            .addOnCompleteListener { createUserTask ->
                if (createUserTask.isSuccessful) {
                    snackbar.use(R.string.snackbar_sucess_sign_in, false)

                    val user = createUserTask.result.user
                    Database.setPersonData(
                        user,
                        personData,
                        snackbar
                    )
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

                snackbar.use(errorMsgInt, true)
            }
    }

    fun signInWithEmailAndPassword(email: String, passw: String, actv: Activity, snackbar: OurSnackbar) {
        auth.signInWithEmailAndPassword(email, passw)
            .addOnCompleteListener { authentication ->
                if (authentication.isSuccessful) {
                    ActvChanger.use(actv, MainViewActivity::class.java, true)
                }
            }.addOnFailureListener { exception ->
                val errorMsgInt = when (exception) {
                    is FirebaseNetworkException -> R.string.snackbar_fail_conection
                    is FirebaseAuthInvalidCredentialsException -> R.string.snackbar_fail_invalid_credential
                    else -> R.string.snackbar_fail_generic_auth
                }
                snackbar.use(errorMsgInt, true)
            }
    }

    fun signInWithCredential(credential: AuthCredential, actv: Activity) {
        auth.signInWithCredential(credential).addOnCompleteListener { authentication ->
            if (authentication.isSuccessful) {
                ActvChanger.use(actv, MainViewActivity::class.java, true)
            }
        }
    }

}