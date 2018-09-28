package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.system.moneycontrol.data.mappers.TransactionFirebase
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.infrastructure.Result
import com.system.moneycontrol.model.entities.Transaction

/**
 * @param collection: Firebase Firestore (transactions)
 */
class TransactionRepository(private val collection: CollectionReference, var myUtils: MyUtils) {

    fun getList(year: String, month: String) = object : Result<Transaction>() {

        override fun execute() {
            collection.document(year).collection(month).orderBy("paymentDate").get()
                    .addOnSuccessListener { task -> onSuccessList?.invoke(task.documents.map { getModel(it) }) }
                    .addOnFailureListener { onFailure?.invoke(it) }
        }
    }

    fun save(model: Transaction) = object : Result<Transaction>() {

        override fun execute() {
            collection.document(getYear(model)).collection(getMonth(model)).add(model.toMapper())
                    .addOnCompleteListener { onSuccessItem?.invoke(model.copy(key = it.result.id)) }
                    .addOnFailureListener { onFailure?.invoke(it) }
        }
    }

    fun move(model: Transaction) = object : Result<Transaction>() {

        override fun execute() {
            save(model).addSuccessItem { saved ->
                delete(saved.copy(paymentDate = model.paymentDateOlder))
                        .addSuccessItem { onSuccessItem?.invoke(saved) }
                        .addFailure { onFailure?.invoke(it) }
            }.addFailure { onFailure?.invoke(it) }
        }
    }

    fun delete(model: Transaction) = object : Result<Transaction>() {

        override fun execute() {
            collection.document(getYear(model)).collection(getMonth(model)).document(model.key!!).delete()
                    .addOnSuccessListener { onSuccessItem?.invoke(model) }
                    .addOnFailureListener { onFailure?.invoke(it) }
        }
    }

    fun update(model: Transaction) = object : Result<Transaction>() {

        override fun execute() {
            collection.document(getYear(model)).collection(getMonth(model)).add(model.toMapper())
                    .addOnSuccessListener { onSuccessItem?.invoke(model) }
                    .addOnFailureListener { onFailure?.invoke(it) }
        }
    }

    fun getModel(it: DocumentSnapshot) = it.toObject(TransactionFirebase::class.java)!!.toModel(it.id)

    private fun getMonth(model: Transaction) = myUtils.getDate(model.paymentDate, "MM")

    private fun getYear(model: Transaction) = myUtils.getDate(model.paymentDate, "yyyy")
}