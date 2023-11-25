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

            val ciroDoc = db.collection("Usuários")
                .document("ciro")

            mainviewButtonCreate.setOnClickListener {
                val ciroMap = hashMapOf(
                    "nome" to "Ciro",
                    "sobrenome" to "de Oliveira",
                    "idade" to 27
                )

                ciroDoc.set(ciroMap)
                    .addOnCompleteListener {
                        Log.d("db", "Sucesso ao salvar")
                    }
                    .addOnFailureListener {
                        /** por hora vazio*/
                    }
            }

            mainviewButtonRead.setOnClickListener {
                ciroDoc.addSnapshotListener { doc, except ->
                    if (doc != null) {
                        val txt = binding.mainviewTxtData
                        txt.text = doc.getString("nome")
                    }
                }
            }

            mainviewButtonUpdate.setOnClickListener {
                ciroDoc.update("sobrenome", "Domingos")
                    .addOnCompleteListener {
                        Log.d("db", "Sucesso a atualizar os dados do usuário")
                    }
                    .addOnFailureListener {
                        /** Nada até então*/
                    }
            }

            mainviewButtonDelete.setOnClickListener {
                ciroDoc.delete().addOnCompleteListener {
                    Log.d("db_delete", "Sucesso ao deletar")
                }
            }
        }
    }
}