package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.model.entities.TagGroup

/**
 * @param collection: Firebase Firestore (tagsgroup)
 */
class TagGroupRepository(val collection: CollectionReference) {

    suspend fun findAll(): List<TagGroup> = GenericRepository.findAll(collection, "order")

    suspend fun getByName(name: String): TagGroup = GenericRepository.getBy(collection, "name", name)

    suspend fun delete(model: TagGroup) = GenericRepository.delete(collection, model)

    suspend fun save(model: TagGroup) = GenericRepository.save(collection, model)

    suspend fun update(model: TagGroup) = GenericRepository.update(collection, model)
}