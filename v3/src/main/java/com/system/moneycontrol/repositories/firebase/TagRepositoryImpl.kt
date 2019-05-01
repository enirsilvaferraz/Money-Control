package com.system.moneycontrol.repositories.firebase

import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.data.Tag
import com.system.moneycontrol.repositories.TagRepository

class TagRepositoryImpl(private val collection: CollectionReference) : TagRepository {

    override suspend fun findAll(order: String): List<Tag> =
            FirebaseFirestoreImpl.findAll(collection, order)

    override suspend fun getBy(field: String, value: String): List<Tag> =
            FirebaseFirestoreImpl.getBy(collection, field, value)

    override suspend fun save(model: Tag): Tag =
            FirebaseFirestoreImpl.save(collection, model)

    override suspend fun delete(key: String, model: Tag): Tag =
            FirebaseFirestoreImpl.delete(collection, key, model)

    override suspend fun update(key: String, model: Tag): Tag =
            FirebaseFirestoreImpl.update(collection, key, model)
}