package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
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
                    .addOnSuccessListener { task ->
                        onSuccessList?.invoke(task.documents.map { getModel(it) })
                    }
                    .addOnFailureListener { onFailure?.invoke(it) }

        }
    }

    fun save(model: Tag) = object : Result<Tag>() {

        override fun execute() {
            collection.add(model.toMapper())
                    .addOnSuccessListener { onSuccessItem?.invoke(model.copy(key = it.id)) }
                    .addOnFailureListener { onFailure?.invoke(it) }
        }
    }

    fun delete(model: Tag) = object : Result<Tag>() {

        override fun execute() {
            collection.document(model.key!!).delete()
                    .addOnSuccessListener { onSuccessItem?.invoke(model) }
                    .addOnFailureListener { onFailure?.invoke(it) }
        }
    }

    fun update(model: Tag) = object : Result<Tag>() {

        override fun execute() {
            collection.document(model.key!!).set(model.toMapper())
                    .addOnSuccessListener { onSuccessItem?.invoke(model) }
                    .addOnFailureListener { onFailure?.invoke(it) }
        }
    }

    fun getModel(it: DocumentSnapshot) = it.toObject(TagFirebase::class.java)!!.toModel(it.id)
}