package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

abstract class GenericRepository<T : Any, MT : Any>(private val collection: CollectionReference) {

    suspend fun findAll(order: String): List<T> = suspendCoroutine {

        collection.orderBy(order).get()
                .addOnSuccessListener { task ->
                    it.resume(task.documents.map { model -> getModel(model) })
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }

    }

    suspend fun getBy(field: String, value: String) = suspendCoroutine<T> {

        collection.whereEqualTo(field, value).get()
                .addOnSuccessListener { task ->

                    val documents = task.documents
                    if (!documents.isEmpty()) {
                        it.resume(documents.map { model -> getModel(model) }[0])
                    } else {
                        it.resumeWithException(Exception("Model not found!"))
                    }

                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend fun save(model: T) = suspendCoroutine<String> {

        collection.add(getDataModel(model))
                .addOnSuccessListener { task ->
                    it.resume(task.id)
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend fun delete(key: String) = suspendCoroutine<String> {

        collection.document(key).delete()
                .addOnSuccessListener { _ ->
                    it.resume(key)
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend fun update(key: String, model: T) = suspendCoroutine<String> {

        collection.document(key).set(getDataModel(model))
                .addOnSuccessListener { _ ->
                    it.resume(key)
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    abstract fun getModel(it: DocumentSnapshot): T

    abstract fun getDataModel(model: T): MT
}