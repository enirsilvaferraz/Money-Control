package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.data.mappers.PaymentTypeFirebase
import com.system.moneycontrol.model.entities.PaymentType

/**
 * @param collection: Firebase Firestore (PaymentTypes)
 */
class TypeRepository(private val collection: CollectionReference) {

    suspend fun findAll() = GenericRepository.findAll<PaymentType, PaymentTypeFirebase>(collection, "name")

    suspend fun getByName(name: String) = GenericRepository.getBy<PaymentType, PaymentTypeFirebase>(collection, "name", name)

    suspend fun delete(model: PaymentType) = GenericRepository.delete(collection, model)

    suspend fun save(model: PaymentType) = GenericRepository.save(collection, model)

    suspend fun update(model: PaymentType): PaymentType = GenericRepository.update(collection, model)

    suspend fun getByKey(key: String): PaymentType = GenericRepository.getByKey(collection, key)
}