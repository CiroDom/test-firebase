package com.example.testfirebasestackmobile.ui.form_sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testfirebasestackmobile.R
import com.example.testfirebasestackmobile.core.utils.BlankBarrier
import com.example.testfirebasestackmobile.core.utils.OurSnackBar
import com.example.testfirebasestackmobile.core.singletons.Auth.auth
import com.example.testfirebasestackmobile.core.singletons.ConstRepo
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

            fragaccButtonComplete.setOnClickListener { button ->
                val email = editEmail.text
                val passw = editPassw.text

                fun authenticate() {
                    auth.createUserWithEmailAndPassword(email.toString(), passw.toString())
                        .addOnCompleteListener { createUserTask ->
                            if (createUserTask.isSuccessful) {
                                OurSnackBar.show(
                                    requireContext(),
                                    button,
                                    R.string.snackbar_sucess_sign_in,
                                    false
                                )

                                val user = createUserTask.result.user
                                val firstName = arguments?.getString(ConstRepo.FIRST_NAME_KEY)
                                val secondName = arguments?.getString(ConstRepo.SECOND_NAME_KEY)
                                val personalData = mapOf(
                                    ConstRepo.FIRST_NAME_KEY to firstName,
                                    ConstRepo.SECOND_NAME_KEY to secondName
                                )

                                db.collection(ConstRepo.USER_COLLECTION_KEY)
                                    .document(user!!.uid)
                                    .set(personalData)
                                    .addOnCompleteListener {
                                        OurSnackBar.show(
                                            requireContext(),
                                            button,
                                            R.string.snackbar_sucess_db_persondata,
                                            false
                                        )
                                    }
                                    .addOnFailureListener {
                                        OurSnackBar.show(
                                            requireContext(),
                                            button,
                                            R.string.snackbar_fail_personal_data,
                                            true,
                                        )
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

                            OurSnackBar.show(
                                requireContext(),
                                button,
                                errorMsgInt,
                                true
                            )
                        }
                }


                if (BlankBarrier.canItPass(
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