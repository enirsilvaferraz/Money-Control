package com.system.moneycontrol.model.repositories

import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.mappers.TransactionMapper
import javax.inject.Inject

/**
 * @param collection: Firebase Firestore (transactions)
 */
class TransactionRepository @Inject constructor(val collection: CollectionReference) {

    fun getList(year: Int, month: Int, onSuccess: ((List<Transaction>) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.document(year.toString()).collection(month.toString()).get()
                .addOnCompleteListener { task ->
                    onSuccess?.invoke(task.result.documents.map {
                        it.toObject(TransactionMapper::class.java).toModel()
                    })
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    fun save(model: Transaction, onSuccess: ((Transaction) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        model.newKey()

        collection.document(getYear(model)).collection(getMonth(model)).add(model.toMapper())
                .addOnSuccessListener {
                    onSuccess?.invoke(it.get().result.toObject(TransactionMapper::class.java).toModel())
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    fun delete(model: Transaction, onSuccess: ((Transaction) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.document(getYear(model)).collection(getMonth(model)).document(model.key).delete()
                .addOnSuccessListener {
                    onSuccess?.invoke(model)
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    fun update(model: Transaction, onSuccess: ((Transaction) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.document(getYear(model)).collection(getMonth(model)).add(model.toMapper())
                .addOnSuccessListener {
                    onSuccess?.invoke(it.get().result.toObject(TransactionMapper::class.java).toModel())
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    private fun getMonth(model: Transaction) = MyUtils.getDate(model.paymentDate, "MM")

    private fun getYear(model: Transaction) = MyUtils.getDate(model.paymentDate, "yyyy")
}