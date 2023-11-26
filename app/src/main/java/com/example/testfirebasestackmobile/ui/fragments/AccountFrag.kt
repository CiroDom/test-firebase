package com.example.testfirebasestackmobile.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testfirebasestackmobile.R
import com.example.testfirebasestackmobile.core.helpers.BlankBarrier
import com.example.testfirebasestackmobile.core.helpers.OurSnackBar
import com.example.testfirebasestackmobile.core.singletons.Auth
import com.example.testfirebasestackmobile.core.singletons.Auth.auth
import com.example.testfirebasestackmobile.core.singletons.Database
import com.example.testfirebasestackmobile.core.singletons.Database.db
import com.example.testfirebasestackmobile.databinding.FragAccountBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class AccountFrag : Fragment() {

    private val binding by lazy {
        FragAccountBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val editEmail = fragaccEditEmail
            val editPassw = fragaccEditPassw

            fragaccButtonNext.setOnClickListener { button ->
                val email = editEmail.text
                val passw = editPassw.text

                fun authenticate() {
                    auth.createUserWithEmailAndPassword(email.toString(), passw.toString())
                        .addOnCompleteListener { signIn ->
                            if (signIn.isSuccessful) {
                                val firstName = arguments?.getString(PersonFrag.FIRST_NAME_KEY)
                                val secondName = arguments?.getString(PersonFrag.SECOND_NAME_KEY)


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

                            val ourSnackBar = OurSnackBar()
                            ourSnackBar.show(
                                requireContext(),
                                button,
                                errorMsgInt,
                                true
                            )
                        }
                }

                val blankBarrier = BlankBarrier()

                if (blankBarrier.canItPass(
                        requireContext(),
                        button,
                        editEmail,
                        editPassw,
                    )
                ) {
                    authenticate()
                }
            }
        }
    }
}