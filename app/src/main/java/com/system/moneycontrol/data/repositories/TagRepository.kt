package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.system.moneycontrol.data.mappers.TagFirebase
import com.system.moneycontrol.infrastructure.Result
import com.system.moneycontrol.model.entities.Tag

/**
 * @param collection: Firebase Firestore (tags)
 */
class TagRepository(val collection: CollectionReference) {

    fun getList() = object : Result<Tag>() {

        override fun execute() {
            collection.orderBy("name").get()
                    .addOnSuccessListener { task ->
                        onSuccessList?.invoke(task.documents.map { getModel(it) })
                    }
                    .addOnFailureListener { onFailure?.invoke(it) }
        }
    }

    fun getByName(name: String) = object : Result<Tag>() {

        override fun execute() {
            collection.whereEqualTo("name", name).get()
                    .addOnSuccessListener { task ->

                        val documents = task.documents
                        if (!documents.isEmpty()) {
                            onSuccessItem?.invoke(documents.map { getModel(it) }.get(0))
                        } else {
                            onWarning?.invoke("Model not found!")
                        }

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