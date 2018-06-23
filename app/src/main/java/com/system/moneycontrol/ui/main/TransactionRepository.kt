package com.system.moneycontrol.ui.main

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.entities.Transaction
import javax.inject.Inject

class TransactionRepository @Inject constructor(val collection: CollectionReference) : MainContract.Repository {

    override fun getList(year: Int, month: Int, function: (List<Transaction>) -> Unit) {

        collection.document(year.toString()).collection(month.toString()).get()
                .addOnCompleteListener { task ->
                    function.invoke(task.getResult().documents.map { it.toObject(Transaction::class.java) })
                }
                .addOnFailureListener { Log.i("TAG", "Falha!") }
    }
}