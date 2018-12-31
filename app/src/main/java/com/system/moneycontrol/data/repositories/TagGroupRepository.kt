package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.system.moneycontrol.data.mappers.GroupTagFirebase
import com.system.moneycontrol.model.entities.TagGroup
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @param collection: Firebase Firestore (tagsgroup)
 */
class TagGroupRepository(val collection: CollectionReference) {

    suspend fun getList() = suspendCoroutine<List<TagGroup>> {

        collection.orderBy("order").get()
                .addOnSuccessListener { task ->
                    it.resume(task.documents.map { getModel(it) })
                }
                .addOnFailureListener { exception ->
                    it.resumeWithException(exception)
                }
    }

    private fun getModel(it: DocumentSnapshot) = it.toObject(GroupTagFirebase::class.java)!!.toModel(it.id)
}