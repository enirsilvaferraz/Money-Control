package com.system.moneycontrol.model.repositories

import com.google.firebase.firestore.CollectionReference
import com.system.moneycontrol.di.ConstantsDI
import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.mappers.PaymentTypeMapper
import javax.inject.Inject
import javax.inject.Named

/**
 * @param collection: Firebase Firestore (PaymentTypes)
 */
class PaymentTypeRepository @Inject constructor(@Named(ConstantsDI.FIRESTORE_PAYMENTTYPE) private val collection: CollectionReference) {

    fun getList(onSuccess: ((List<PaymentType>) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.get()
                .addOnSuccessListener { task ->
                    onSuccess?.invoke(task.documents.map {
                        it.toObject(PaymentTypeMapper::class.java)!!.toModel(it.id)
                    })
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    fun save(model: PaymentType, onSuccess: ((PaymentType) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.add(model.toMapper())
                .addOnSuccessListener {
                    onSuccess?.invoke(model.copy(key = it.id))
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    fun delete(model: PaymentType, onSuccess: ((PaymentType) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.document(model.key).delete()
                .addOnSuccessListener {
                    onSuccess?.invoke(model)
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }

    fun update(model: PaymentType, onSuccess: ((PaymentType) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        collection.document(model.key).set(model.toMapper())
                .addOnSuccessListener {
                    onSuccess?.invoke(model)
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
    }
}