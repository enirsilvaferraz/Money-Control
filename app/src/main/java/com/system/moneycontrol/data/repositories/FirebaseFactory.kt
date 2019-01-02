package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

object FirebaseFactory {

    fun instence(): DocumentReference {
        val instance = FirebaseFirestore.getInstance()
        instance.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true).setTimestampsInSnapshotsEnabled(true).build()
        return instance.collection("APP").document("release")
    }
}