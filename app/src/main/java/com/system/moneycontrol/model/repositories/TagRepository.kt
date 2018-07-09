package com.system.moneycontrol.model.repositories

import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.di.ConstantsDI
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.mappers.TagMapper
import javax.inject.Inject
import javax.inject.Named

/**
 * @param collection: Firebase Firestore (tags)
 */
class TagRepository @Inject constructor(@Named(ConstantsDI.FIRESTORE_TAG) val collection: CollectionReference) {

    fun getList(onSuccess: ((List<Tag>) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.get()
                .addOnCompleteListener { task ->
                    onSuccess?.invoke(task.result.documents.map {
                        it.toObject(TagMapper::class.java)!!.toModel(it.id)
                    })
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    fun save(model: Tag, onSuccess: ((Tag) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.add(model.toMapper())
                .addOnSuccessListener {
                    onSuccess?.invoke(model.copy(key = it.id))
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    fun delete(model: Tag, onSuccess: ((Tag) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.document(model.key).delete()
                .addOnSuccessListener {
                    onSuccess?.invoke(model)
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    fun update(model: Tag, onSuccess: ((Tag) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.document(model.key).set(model.toMapper())
                .addOnSuccessListener {
                    onSuccess?.invoke(model)
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }
}