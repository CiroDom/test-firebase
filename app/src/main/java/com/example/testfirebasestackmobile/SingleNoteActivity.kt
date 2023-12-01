package com.example.testfirebasestackmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testfirebasestackmobile.databinding.ActivitySingleNoteBinding

class SingleNoteActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySingleNoteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



    }
}