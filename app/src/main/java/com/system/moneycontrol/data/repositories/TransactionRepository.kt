package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.system.moneycontrol.data.mappers.TransactionFirebase
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Transaction
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @param collection: Firebase Firestore (transactions)
 */
class TransactionRepository(private val collection: CollectionReference, var myUtils: MyUtils) {

    suspend fun getList(year: String, month: String) = suspendCoroutine<List<Transaction>> {

        collection.document(year).collection(month).orderBy("paymentDate").get()
                .addOnSuccessListener { task ->
                    it.resume(task.documents.map { getModel(it) })
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend fun save(model: Transaction) = suspendCoroutine<Transaction> {

        collection.document(getYear(model)).collection(getMonth(model)).add(model.toMapper())
                .addOnSuccessListener { task ->
                    it.resume(model.copy(key = task.id))
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }


    suspend fun delete(model: Transaction) = suspendCoroutine<Transaction> {

        collection.document(getYear(model)).collection(getMonth(model)).document(model.key!!).delete()
                .addOnSuccessListener { _ ->
                    it.resume(model)
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend fun move(model: Transaction) = delete(save(model).copy(paymentDate = model.paymentDateOlder))

    suspend fun update(model: Transaction) = suspendCoroutine<Transaction> {

        collection.document(getYear(model)).collection(getMonth(model)).document(model.key!!).update(model.toMapper().toMap())
                .addOnSuccessListener { _ ->
                    it.resume(model)
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    private fun getModel(it: DocumentSnapshot) = it.toObject(TransactionFirebase::class.java)!!.toModel(it.id)

    private fun getMonth(model: Transaction) = myUtils.getDate(model.paymentDate, "MM")

    private fun getYear(model: Transaction) = myUtils.getDate(model.paymentDate, "yyyy")
}