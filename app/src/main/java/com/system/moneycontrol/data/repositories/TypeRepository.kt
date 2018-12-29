package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.system.moneycontrol.data.mappers.PaymentTypeFirebase
import com.system.moneycontrol.model.entities.PaymentType
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @param collection: Firebase Firestore (PaymentTypes)
 */
class TypeRepository(private val collection: CollectionReference) {

    suspend fun getList() = suspendCoroutine<List<PaymentType>> {

        collection.orderBy("name").get()
                .addOnSuccessListener { task ->
                    it.resume(task.documents.map { getModel(it) })
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend fun getByName(name: String) = suspendCoroutine<PaymentType> {

        collection.whereEqualTo("name", name).get()
                .addOnSuccessListener { task ->

                    val documents = task.documents
                    if (!documents.isEmpty()) {
                        it.resume(documents.map { getModel(it) }.get(0))
                    } else {
                        it.resumeWithException(Exception("Model not found!"))
                    }

                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend fun save(model: PaymentType) = suspendCoroutine<PaymentType> {

        collection.add(model.toMapper())
                .addOnSuccessListener { task ->
                    it.resume(model.copy(key = task.id))
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend fun delete(model: PaymentType) = suspendCoroutine<PaymentType> {

        collection.document(model.key!!).delete()
                .addOnSuccessListener { _ ->
                    it.resume(model)
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend fun update(model: PaymentType) = suspendCoroutine<PaymentType> {

        collection.document(model.key!!).set(model.toMapper())
                .addOnSuccessListener { _ ->
                    it.resume(model)
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    fun getModel(it: DocumentSnapshot) = it.toObject(PaymentTypeFirebase::class.java)!!.toModel(it.id)
}