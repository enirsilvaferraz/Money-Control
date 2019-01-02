package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.model.entities.Tag

/**
 * @param collection: Firebase Firestore (tags)
 */
class TagRepository(private val collection: CollectionReference) {

    suspend fun findAll(): List<Tag> = GenericRepository.findAll(collection, "name")

    suspend fun getByName(name: String): Tag = GenericRepository.getBy(collection, "name", name)

    suspend fun delete(model: Tag) = GenericRepository.delete(collection, model)

    suspend fun save(model: Tag) = GenericRepository.save(collection, model)

    suspend fun update(model: Tag) = GenericRepository.update(collection, model)

    suspend fun getByKey(key: String): Tag = GenericRepository.getByKey(collection, key)

}