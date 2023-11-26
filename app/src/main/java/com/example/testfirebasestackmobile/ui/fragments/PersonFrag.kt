package com.example.testfirebasestackmobile.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testfirebasestackmobile.R
import com.example.testfirebasestackmobile.core.helpers.BlankBarrier
import com.example.testfirebasestackmobile.databinding.FragPersonBinding

class PersonFrag : Fragment() {

    companion object {
        const val FIRST_NAME_KEY = "first name"
        const val SECOND_NAME_KEY = "second name"
    }

    private val binding by lazy {
        FragPersonBinding.inflate(layoutInflater)
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
            val editFirstName = fragpersonEditFirstname
            val editSecondName = fragpersonEditSecondname

            fragpersonButtonComplete.setOnClickListener { button ->
                fun nextStep() {
                    val bundle = Bundle()
                    with(bundle) {
                        putString(FIRST_NAME_KEY, editFirstName.text.toString())
                        putString(SECOND_NAME_KEY, editSecondName.text.toString())
                    }

                    val navController = findNavController()
                    navController.navigate(R.id.nav_act_person_to_account, bundle)
                }

                val blankBarrier = BlankBarrier()

                val canItPass =
                    blankBarrier.canItPass(
                        requireContext(),
                        button,
                        editFirstName,
                        editSecondName,
                    )

                if (canItPass) {
                    nextStep()
                }
            }
        }
    }
}