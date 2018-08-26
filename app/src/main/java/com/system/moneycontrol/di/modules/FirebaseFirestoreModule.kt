package com.system.moneycontrol.di.modules

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.system.moneycontrol.di.ConstantsDI
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module
class FirebaseFirestoreModule {

    @Named(ConstantsDI.FIRESTORE_TRANSACTION)
    @Provides
    fun provideFirebaseTransaction(): CollectionReference = getFirestoreInstance().collection("transactions")

    @Named(ConstantsDI.FIRESTORE_TAG)
    @Provides
    fun provideFirebaseTag(): CollectionReference = getFirestoreInstance().collection("tags")

    @Named(ConstantsDI.FIRESTORE_PAYMENTTYPE)
    @Provides
    fun provideFirebasePaymentType(): CollectionReference = getFirestoreInstance().collection("types")

    private fun getFirestoreInstance(): FirebaseFirestore {
        val instance = FirebaseFirestore.getInstance()
//        instance.firestoreSettings = FirebaseFirestoreSettings.Builder()
//                .setPersistenceEnabled(true)
//                .build()
        return instance
    }
}