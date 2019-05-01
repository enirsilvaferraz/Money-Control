package com.system.moneycontrol.v3.repositories.firebase

import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.v3.data.Account
import com.system.moneycontrol.v3.repositories.AccountRepository

class AccountRepositoryImpl(private val collection: CollectionReference) : AccountRepository {

    override suspend fun findAll(order: String): List<Account> =
            FirebaseFirestoreImpl.findAll(collection, order)

    override suspend fun getBy(field: String, value: String): List<Account> =
            FirebaseFirestoreImpl.getBy(collection, field, value)

    override suspend fun save(model: Account): Account =
            FirebaseFirestoreImpl.save(collection, model)

    override suspend fun delete(key: String, model: Account): Account =
            FirebaseFirestoreImpl.delete(collection, key, model)

    override suspend fun update(key: String, model: Account): Account =
            FirebaseFirestoreImpl.update(collection, key, model)
}