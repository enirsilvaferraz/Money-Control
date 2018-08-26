package com.system.moneycontrol.model.repositories

import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.di.ConstantsDI
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.mappers.TransactionFirebase
import javax.inject.Inject
import javax.inject.Named

/**
 * @param collection: Firebase Firestore (transactions)
 */
class TransactionRepository @Inject constructor(
        @Named(ConstantsDI.FIRESTORE_TRANSACTION) private val collection: CollectionReference,
        var myUtils: MyUtils) {

    fun getList(year: String, month: String, onSuccess: ((List<Transaction>) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.document(year).collection(month).orderBy("paymentDate").get()
                .addOnSuccessListener { task ->
                    onSuccess?.invoke(task.documents.map {
                        it.toObject(TransactionFirebase::class.java)!!.toModel(it.id)
                    })
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    fun save(model: Transaction, onSuccess: ((Transaction) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.document(getYear(model)).collection(getMonth(model)).add(model.toMapper())
                .addOnSuccessListener {
                    onSuccess?.invoke(model.copy(key = it.id))
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    fun delete(model: Transaction, onSuccess: ((Transaction) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.document(getYear(model)).collection(getMonth(model)).document(model.key!!).delete()
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
                    onSuccess?.invoke(it.get().result.toObject(TransactionFirebase::class.java)!!.toModel(it.id))
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    private fun getMonth(model: Transaction) = myUtils.getDate(model.paymentDate!!, "MM")

    private fun getYear(model: Transaction) = myUtils.getDate(model.paymentDate!!, "yyyy")
}