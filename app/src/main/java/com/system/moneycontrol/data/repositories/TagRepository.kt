package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.system.moneycontrol.data.mappers.TagFirebase
import com.system.moneycontrol.model.entities.Tag

/**
 * @param collection: Firebase Firestore (tags)
 */
class TagRepository(val collection: CollectionReference) : GenericRepository<Tag, TagFirebase>(collection) {

    override fun getModel(it: DocumentSnapshot) = it.toObject(TagFirebase::class.java)!!.toModel(it.id)

    override fun getDataModel(model: Tag): TagFirebase = TagFirebase(name = model.name, group = model.group.key!!)

}