package com.example.testfirebasestackmobile.core.singletons

import com.google.firebase.firestore.FirebaseFirestore

object Database {

    val db = FirebaseFirestore.getInstance()

}