package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.system.moneycontrol.data.mappers.TagGroupFirebase
import com.system.moneycontrol.model.entities.TagGroup

/**
 * @param collection: Firebase Firestore (tagsgroup)
 */
class TagGroupRepository(val collection: CollectionReference) : GenericRepository<TagGroup, TagGroupFirebase>(collection) {

    suspend fun findAll() = super.findAll("order")

    override fun getDataModel(model: TagGroup): TagGroupFirebase = TagGroupFirebase(model.name)

    override fun getModel(it: DocumentSnapshot) = it.toObject(TagGroupFirebase::class.java)!!.toModel(it.id)
}