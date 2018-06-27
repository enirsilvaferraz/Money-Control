package com.system.moneycontrol.model.repositories

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.mappers.TransactionMapper
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * @param collection: Firebase Firestore (transactions)
 */
open class TransactionRepository @Inject constructor(val collection: CollectionReference) {

    open fun getList(year: Int, month: Int, function: (List<Transaction>) -> Unit) {

        collection.document(year.toString()).collection(month.toString()).get()
                .addOnCompleteListener { task ->
                    function.invoke(task.result.documents.map {
                        it.toObject(TransactionMapper::class.java).toModel()
                    })
                }
                .addOnFailureListener {
                    Log.i("TAG", "Falha!")
                }
    }

    open fun save(model: Transaction, onSuccess: ((Transaction) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        val year = SimpleDateFormat("yyyy", Locale.ENGLISH).format(model.paymentDate)
        val month = SimpleDateFormat("MM", Locale.ENGLISH).format(model.paymentDate)

        collection.document(year).collection(month).add(model.toMapper())
                .addOnSuccessListener {
                    onSuccess?.invoke(it.get().result.toObject(TransactionMapper::class.java).toModel())
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    open fun delete(model: Transaction, onSuccess: ((Transaction) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        val year = SimpleDateFormat("yyyy", Locale.ENGLISH).format(model.paymentDate)
        val month = SimpleDateFormat("MM", Locale.ENGLISH).format(model.paymentDate)

        collection.document(year).collection(month).document(model.key).delete()
                .addOnSuccessListener {
                    onSuccess?.invoke(model)
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    open fun update(model: Transaction, onSuccess: ((Transaction) -> Unit)?, onFailure: ((Exception) -> Unit)?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}