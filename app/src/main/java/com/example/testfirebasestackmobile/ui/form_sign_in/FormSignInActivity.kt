package com.example.testfirebasestackmobile.ui.form_sign_in

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testfirebasestackmobile.core.utils.KeyboardHider
import com.example.testfirebasestackmobile.databinding.ActivityFormSignInBinding

class FormSignInActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormSignInBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val parentLay = binding.singInLinlayParent

        parentLay.setOnClickListener {
            KeyboardHider.use(this)
        }
    }
}