package com.system.moneycontrol.infrastructure.koin

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.system.moneycontrol.data.repositories.TagGroupRepository
import com.system.moneycontrol.data.repositories.TagRepository
import com.system.moneycontrol.data.repositories.TransactionRepository
import com.system.moneycontrol.data.repositories.TypeRepository
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.infrastructure.MyViewUtils
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.business.TagBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
import com.system.moneycontrol.model.business.TypeBusiness
import com.system.moneycontrol.ui.presentation.home.HomeContract
import com.system.moneycontrol.ui.presentation.home.HomePresenter
import com.system.moneycontrol.ui.presentation.tag.TagManagerContract
import com.system.moneycontrol.ui.presentation.tag.TagManagerPresenter
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerContract
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerPresenter
import com.system.moneycontrol.ui.presentation.typemanager.TypeManagerContract
import com.system.moneycontrol.ui.presentation.typemanager.TypeManagerPresenter
import org.koin.dsl.module.module

object KoinModules {

    fun getFirestoreInstance(): DocumentReference {
        val instance = FirebaseFirestore.getInstance()
        instance.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true).setTimestampsInSnapshotsEnabled(true).build()
        return instance.collection("APP").document("release")
    }

    val firebaseModule = module {
        single(name = ConstantsDI.FIRESTORE_TAG) { getFirestoreInstance().collection("tags") }
        single(name = ConstantsDI.FIRESTORE_TAGGROUP) { getFirestoreInstance().collection("tagGroup") }
        single(name = ConstantsDI.FIRESTORE_PAYMENTTYPE) { getFirestoreInstance().collection("types") }
        single(name = ConstantsDI.FIRESTORE_TRANSACTION) { getFirestoreInstance().collection("transactions") }
    }

    val repositoryModule = module {
        single { TagRepository(collection = get(name = ConstantsDI.FIRESTORE_TAG)) }
        single { TagGroupRepository(collection = get(name = ConstantsDI.FIRESTORE_TAGGROUP)) }
        single { TypeRepository(collection = get(name = ConstantsDI.FIRESTORE_PAYMENTTYPE)) }
        single { TransactionRepository(collection = get(name = ConstantsDI.FIRESTORE_TRANSACTION), myUtils = get()) }
    }

    val businessModule = module {
        single { TagBusiness(repository = get(), groupRepository = get()) }
        single { TypeBusiness(get()) }
        single { TransactionBusiness(repository = get(), tagBusiness = get(), typeBusiness = get(), myUtils = get()) }
        single { HomeBusiness(repTransaction = get(), tagBusiness = get(), typeBusiness = get(), repTagGroup = get()) }
    }

    val appModule = module {
        single { MyUtils() }
        single { MyViewUtils() }
    }

    val presenterModule = module {
        factory { (view: HomeContract.View) -> HomePresenter(view, get(), get(), get()) as HomeContract.Presenter }
        factory { (view: TagManagerContract.View) -> TagManagerPresenter(view, get()) as TagManagerContract.Presenter }
        factory { (view: TypeManagerContract.View) -> TypeManagerPresenter(view, get()) as TypeManagerContract.Presenter }
        factory { (view: TransactionManagerContract.View) -> TransactionManagerPresenter(view, get(), get(), get(), get()) as TransactionManagerContract.Presenter }
    }

}