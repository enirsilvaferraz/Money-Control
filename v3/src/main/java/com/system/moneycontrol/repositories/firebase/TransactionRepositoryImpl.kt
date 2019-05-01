package com.system.moneycontrol.repositories.firebase

import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.repositories.TransactionRepository
import java.util.*

class TransactionRepositoryImpl(private val collection: CollectionReference) : TransactionRepository {

    override suspend fun findAll(order: String, start: Date?, end: Date?): List<Transaction> =
            FirebaseFirestoreImpl.findAll(collection, order, start, end)

    override suspend fun getBy(field: String, value: String): List<Transaction> =
            FirebaseFirestoreImpl.getBy(collection, field, value)

    override suspend fun save(model: Transaction): Transaction =
            FirebaseFirestoreImpl.save(collection, model)

    override suspend fun delete(key: String, model: Transaction): Transaction =
            FirebaseFirestoreImpl.delete(collection, key, model)

    override suspend fun update(key: String, model: Transaction): Transaction =
            FirebaseFirestoreImpl.delete(collection, key, model)
}