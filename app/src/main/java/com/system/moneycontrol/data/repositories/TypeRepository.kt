package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.system.moneycontrol.data.mappers.PaymentTypeFirebase
import com.system.moneycontrol.model.entities.PaymentType

/**
 * @param collection: Firebase Firestore (PaymentTypes)
 */
class TypeRepository(private val collection: CollectionReference) : GenericRepository<PaymentType, PaymentTypeFirebase>(collection) {

    suspend fun findAll() = super.findAll("name")

    suspend fun getByName(name: String) = getBy("name", name)

    override fun getModel(it: DocumentSnapshot) = it.toObject(PaymentTypeFirebase::class.java)!!.toModel(it.id)

    override fun getDataModel(model: PaymentType): PaymentTypeFirebase = PaymentTypeFirebase(model.name, model.color)
}