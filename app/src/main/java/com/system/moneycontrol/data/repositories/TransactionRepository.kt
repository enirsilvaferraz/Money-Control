package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Transaction

/**
 * @param collection: Firebase Firestore (transactions)
 */
class TransactionRepository(private val collection: CollectionReference, var myUtils: MyUtils) {

    suspend fun findAll(year: String, month: String): List<Transaction> = GenericRepository.findAll(getColl(year, month), "paymentDate")

    suspend fun delete(model: Transaction) = GenericRepository.delete(getColl(getYear(model), getMonth(model)), model)

    suspend fun save(model: Transaction) = GenericRepository.save(getColl(getYear(model), getMonth(model)), model)

    suspend fun update(model: Transaction) = GenericRepository.update(getColl(getYear(model), getMonth(model)), model)

    suspend fun move(model: Transaction) = delete(save(model).copy(paymentDate = model.paymentDateOlder))

    private fun getMonth(model: Transaction) = myUtils.getDate(model.paymentDate, "MM")

    private fun getYear(model: Transaction) = myUtils.getDate(model.paymentDate, "yyyy")

    private fun getColl(year: String, month: String) = collection.document(year).collection(month)
}