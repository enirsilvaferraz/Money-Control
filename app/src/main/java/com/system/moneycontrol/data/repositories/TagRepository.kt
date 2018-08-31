package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.data.mappers.TagFirebase
import com.system.moneycontrol.di.ConstantsDI
import com.system.moneycontrol.infrastructure.Result
import com.system.moneycontrol.model.entities.Tag
import javax.inject.Inject
import javax.inject.Named

/**
 * @param collection: Firebase Firestore (tags)
 */
class TagRepository @Inject constructor(@Named(ConstantsDI.FIRESTORE_TAG) val collection: CollectionReference) {

    fun getList() = object : Result<Tag>() {

        override fun execute() {

            collection.get()
                    .addOnFailureListener {
                        onFailure?.invoke(it)
                    }
                    .addOnSuccessListener { task ->
                        onSuccessList?.invoke(task.documents.map {
                            it.toObject(TagFirebase::class.java)!!.toModel(it.id)
                        })
                    }
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