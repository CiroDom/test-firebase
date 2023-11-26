package com.example.testfirebasestackmobile.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testfirebasestackmobile.databinding.ActivityMainViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainViewActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainViewBinding.inflate(layoutInflater)
    }

    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            mainviewButtonExit.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@MainViewActivity, FormLoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}