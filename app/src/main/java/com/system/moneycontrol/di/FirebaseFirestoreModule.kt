package com.system.moneycontrol.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
class FirebaseFirestoreModule {

    @Provides
    fun provideFirebaseTransaction(): CollectionReference = FirebaseFirestore.getInstance().collection("transactions")
}