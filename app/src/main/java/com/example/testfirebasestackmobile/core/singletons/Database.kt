package com.example.testfirebasestackmobile.core.singletons

import com.example.testfirebasestackmobile.R
import com.example.testfirebasestackmobile.core.utils.ConstRepo
import com.example.testfirebasestackmobile.core.utils.OurSnackbar
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

object Database {

    private val db = FirebaseFirestore.getInstance()

    fun setPersonData(user: FirebaseUser?, personalData: Map<String, String?>, snackbar: OurSnackbar,) {
        db.collection(ConstRepo.USER_COLLECTION_KEY)
            .document(user!!.uid)
            .set(personalData)
            .addOnCompleteListener {
                snackbar.use(R.string.snackbar_sucess_db_persondata, false)
            }
            .addOnFailureListener {
                snackbar.use(R.string.snackbar_fail_db_personal_data, true)
            }
    }

}