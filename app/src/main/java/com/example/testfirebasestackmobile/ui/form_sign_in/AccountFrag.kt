package com.example.testfirebasestackmobile.ui.form_sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testfirebasestackmobile.R
import com.example.testfirebasestackmobile.core.singletons.Auth
import com.example.testfirebasestackmobile.core.utils.BlankBarrier
import com.example.testfirebasestackmobile.core.utils.OurSnackbar
import com.example.testfirebasestackmobile.core.utils.ConstRepo
import com.example.testfirebasestackmobile.databinding.FragAccountBinding

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

                val firstName = arguments?.getString(ConstRepo.FIRST_NAME_KEY)
                val secondName = arguments?.getString(ConstRepo.SECOND_NAME_KEY)
                val personData = mapOf<String, String?>(
                    ConstRepo.FIRST_NAME_KEY to firstName,
                    ConstRepo.SECOND_NAME_KEY to secondName
                )

                val snackbar = OurSnackbar(requireContext(), button)

                if (BlankBarrier.canItPass(
                        snackbar,
                        editEmail,
                        editPassw,
                    )
                ) {
                    Auth.createUserWithEmailAndPassword(
                        email.toString(),
                        passw.toString(),
                        personData,
                        snackbar
                    )
                }
            }
        }
    }
}