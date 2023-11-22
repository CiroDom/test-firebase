package com.example.testfirebasestackmobile.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testfirebasestackmobile.databinding.ActivityMainViewBinding
import com.google.firebase.auth.FirebaseAuth

class MainViewActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainViewBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.mainviewButtonExit.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormLoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}