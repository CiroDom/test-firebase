package com.example.testfirebasestackmobile.ui.main_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testfirebasestackmobile.core.singletons.Auth
import com.example.testfirebasestackmobile.core.singletons.Database
import com.example.testfirebasestackmobile.core.utils.ActvChanger
import com.example.testfirebasestackmobile.core.utils.ConstRepo
import com.example.testfirebasestackmobile.databinding.ActivityMainViewBinding
import com.example.testfirebasestackmobile.ui.form_login.FormLoginActivity
import com.example.testfirebasestackmobile.ui.notes.NotesActivity
import com.google.firebase.auth.FirebaseAuth

class MainViewActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainViewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Database.getUserPersonData(
            Auth.user,
            ConstRepo.FIRST_NAME_KEY,
            binding.mainviewTxtFirstname
            )

        with(binding) {
            mainviewButtonExit.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                ActvChanger.use(this@MainViewActivity, FormLoginActivity::class.java, true)
            }

            mainviewButtonNotesGo.setOnClickListener {
                ActvChanger.use(this@MainViewActivity, NotesActivity::class.java, false)
            }
        }
    }
}