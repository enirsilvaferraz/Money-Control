package com.system.moneycontrol.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.system.moneycontrol.data.mappers.PaymentTypeFirebase
import com.system.moneycontrol.di.ConstantsDI
import com.system.moneycontrol.infrastructure.Result
import com.system.moneycontrol.model.entities.PaymentType
import javax.inject.Inject
import javax.inject.Named

/**
 * @param collection: Firebase Firestore (PaymentTypes)
 */
class TypeRepository @Inject constructor(@Named(ConstantsDI.FIRESTORE_PAYMENTTYPE) private val collection: CollectionReference) {

    fun getList(): Result<PaymentType> = object : Result<PaymentType>() {

        override fun execute() {
            collection.get()
                    .addOnSuccessListener { task ->
                        onSuccessList?.invoke(task.documents.map { getModel(it) })
                    }
                    .addOnFailureListener { onFailure?.invoke(it) }
        }
    }

    fun save(model: PaymentType) = object : Result<PaymentType>() {

        override fun execute() {
            collection.add(model.toMapper())
                    .addOnSuccessListener { onSuccessItem?.invoke(model.copy(key = it.id)) }
                    .addOnFailureListener { onFailure?.invoke(it) }
        }
    }

    fun delete(model: PaymentType) = object : Result<PaymentType>() {

        override fun execute() {
            collection.document(model.key).delete()
                    .addOnSuccessListener { onSuccessItem?.invoke(model) }
                    .addOnFailureListener { onFailure?.invoke(it) }
        }
    }

    fun update(model: PaymentType) = object : Result<PaymentType>() {

        override fun execute() {
            collection.document(model.key).set(model.toMapper())
                    .addOnSuccessListener { onSuccessItem?.invoke(model) }
                    .addOnFailureListener { onFailure?.invoke(it) }
        }
    }

    fun getModel(it: DocumentSnapshot) = it.toObject(PaymentTypeFirebase::class.java)!!.toModel(it.id)
}