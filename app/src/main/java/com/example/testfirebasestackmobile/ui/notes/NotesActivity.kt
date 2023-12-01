package com.example.testfirebasestackmobile.ui.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testfirebasestackmobile.SingleNoteActivity
import com.example.testfirebasestackmobile.core.utils.ActvChanger
import com.example.testfirebasestackmobile.databinding.ActivityNotesBinding

class NotesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNotesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            notesFabAdd.setOnClickListener {
                ActvChanger.use(
                    this@NotesActivity,
                    SingleNoteActivity::class.java,
                    false
                )
            }
        }
    }
}