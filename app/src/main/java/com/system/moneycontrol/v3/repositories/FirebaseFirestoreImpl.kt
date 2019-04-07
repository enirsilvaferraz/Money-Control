package com.system.moneycontrol.v3.repositories

import com.google.firebase.firestore.CollectionReference
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object FirebaseFirestoreImpl {

    suspend inline fun <reified ReturnType : Any> findAll(
            collection: CollectionReference, order: String, start: Date? = null, end: Date? = null) =
            suspendCoroutine<List<ReturnType>> { continuation ->

                val query = collection.orderBy(order)

                if (start != null) query.startAt(start)
                if (end != null) query.endAt(end)

                query.get()
                        .addOnSuccessListener { task ->
                            continuation.resume(task.toObjects(ReturnType::class.java))
                        }
                        .addOnFailureListener { exception ->
                            continuation.resumeWithException(exception)
                        }
            }

    suspend inline fun <reified ReturnType : Any> getBy(collection: CollectionReference, field: String, value: String) =
            suspendCoroutine<List<ReturnType>> { continuation ->

                collection.whereEqualTo(field, value).get()
                        .addOnSuccessListener { task ->
                            continuation.resume(task.toObjects(ReturnType::class.java))
                        }
                        .addOnFailureListener { exception ->
                            continuation.resumeWithException(exception)
                        }
            }

    suspend inline fun <reified ReturnType : Any> save(collection: CollectionReference, model: ReturnType) =
            suspendCoroutine<ReturnType> { continuation ->

                collection.add(model)
                        .addOnSuccessListener { task ->
                            continuation.resume(model)
                        }
                        .addOnFailureListener { exception ->
                            continuation.resumeWithException(exception)
                        }
            }

    suspend inline fun <reified ReturnType : Any> delete(collection: CollectionReference, key: String, model: ReturnType) =
            suspendCoroutine<ReturnType> { continuation ->

                collection.document(key).delete()
                        .addOnSuccessListener { task ->
                            continuation.resume(model)
                        }
                        .addOnFailureListener { exception ->
                            continuation.resumeWithException(exception)
                        }
            }

    suspend inline fun <reified ReturnType : Any> update(collection: CollectionReference, key: String, model: ReturnType) =
            suspendCoroutine<ReturnType> { continuation ->

                collection.document(key).set(model)
                        .addOnSuccessListener {
                            continuation.resume(model)
                        }
                        .addOnFailureListener { exception ->
                            continuation.resumeWithException(exception)
                        }
            }

}
