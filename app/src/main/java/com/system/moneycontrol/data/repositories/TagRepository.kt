package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.system.moneycontrol.data.mappers.TagFirebase
import com.system.moneycontrol.model.entities.Tag
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @param collection: Firebase Firestore (tags)
 */
class TagRepository(val collection: CollectionReference) {

    suspend fun getList() = suspendCoroutine<List<Tag>> {

        collection.orderBy("name").get()
                .addOnSuccessListener { task ->
                    it.resume(task.documents.map { getModel(it) })
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend fun getByName(name: String) = suspendCoroutine<Tag> {

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

    suspend fun save(model: Tag) = suspendCoroutine<Tag> {

        collection.add(model.toMapper())
                .addOnSuccessListener { task ->
                    it.resume(model.copy(key = task.id))
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend fun delete(model: Tag) = suspendCoroutine<Tag> {

        collection.document(model.key!!).delete()
                .addOnSuccessListener { _ ->
                    it.resume(model)
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    suspend fun update(model: Tag) = suspendCoroutine<Tag> {

        collection.document(model.key!!).set(model.toMapper())
                .addOnSuccessListener { _ ->
                    it.resume(model)
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    private fun getModel(it: DocumentSnapshot) = it.toObject(TagFirebase::class.java)!!.toModel(it.id)
}