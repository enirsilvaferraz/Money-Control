package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.data.mappers.DataFire
import com.system.moneycontrol.model.entities.EntityFire
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object GenericRepository {

    suspend inline fun <reified EF : EntityFire<DF>, reified DF : DataFire<EF>> findAll(collection: CollectionReference, order: String): List<EF> = suspendCoroutine {

        collection.orderBy(order).get()
                .addOnSuccessListener { task ->
                    it.resume(task.documents.map { model ->
                        model.toObject(DF::class.java)!!.toEntity(model.id)
                    })
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend inline fun <reified EF : EntityFire<DF>, reified DF : DataFire<EF>> getBy(collection: CollectionReference, field: String, value: String) = suspendCoroutine<EF> {

        collection.whereEqualTo(field, value).get()
                .addOnSuccessListener { task ->
                    val documents = task.documents
                    if (!documents.isEmpty()) {
                        it.resume(documents.map { model ->
                            model.toObject(DF::class.java)!!.toEntity(model.id)
                        }[0])
                    } else {
                        it.resumeWithException(Exception("Model not found!"))
                    }
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend inline fun <reified EF : EntityFire<DF>, reified DF : DataFire<EF>> getByKey(collection: CollectionReference, key: String) = suspendCoroutine<EF> {

        collection.document(key).get()
                .addOnSuccessListener { model ->
                    it.resume(model.toObject(DF::class.java)!!.toEntity(model.id))
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend inline fun <reified EF : EntityFire<DF>, reified DF : DataFire<EF>> save(collection: CollectionReference, model: EF) = suspendCoroutine<EF> {

        collection.add(model.toData())
                .addOnSuccessListener { _ ->
                    it.resume(model)
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend inline fun <reified EF : EntityFire<DF>, reified DF : DataFire<EF>> delete(collection: CollectionReference, model: EF) = suspendCoroutine<EF> {

        collection.document(model.getID()).delete()
                .addOnSuccessListener { _ ->
                    it.resume(model)
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend inline fun <reified EF : EntityFire<DF>, reified DF : DataFire<EF>> update(collection: CollectionReference, model: EF) = suspendCoroutine<EF> {

        collection.document(model.getID()).set(model.toData())
                .addOnSuccessListener { _ ->
                    it.resume(model)
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }
}

