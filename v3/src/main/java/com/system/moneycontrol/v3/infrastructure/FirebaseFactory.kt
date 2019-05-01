package com.system.moneycontrol.v3.infrastructure

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

object FirebaseFactory {

    fun instance(): DocumentReference {
        val instance = FirebaseFirestore.getInstance()
        instance.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true).setTimestampsInSnapshotsEnabled(true).build()
        return instance.collection("APP").document("V3")
    }
}