package com.system.moneycontrol.infrastructure.koin

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.system.moneycontrol.data.repositories.TagRepository
import com.system.moneycontrol.data.repositories.TransactionRepository
import com.system.moneycontrol.data.repositories.TypeRepository
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.business.TagBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
import com.system.moneycontrol.model.business.TypeBusiness
import com.system.moneycontrol.ui.home.HomeContract
import com.system.moneycontrol.ui.home.HomePresenter
import com.system.moneycontrol.ui.tag.TagManagerContract
import com.system.moneycontrol.ui.tag.TagManagerPresenter
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerContract
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerPresenter
import com.system.moneycontrol.ui.typemanager.TypeManagerContract
import com.system.moneycontrol.ui.typemanager.TypeManagerPresenter
import org.koin.dsl.module.module

object KoinModules {

    fun getFirestoreInstance(): FirebaseFirestore {
        val instance = FirebaseFirestore.getInstance()
        instance.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true).setTimestampsInSnapshotsEnabled(true).build()
        return instance
    }

    val firebaseModule = module {
        single(name = ConstantsDI.FIRESTORE_TAG) { getFirestoreInstance().collection("tags") }
        single(name = ConstantsDI.FIRESTORE_PAYMENTTYPE) { getFirestoreInstance().collection("types") }
        single(name = ConstantsDI.FIRESTORE_TRANSACTION) { getFirestoreInstance().collection("transactions") }
    }

    val repositoryModule = module {
        single { TagRepository(get(name = ConstantsDI.FIRESTORE_TAG)) }
        single { TypeRepository(get(name = ConstantsDI.FIRESTORE_PAYMENTTYPE)) }
        single { TransactionRepository(get(name = ConstantsDI.FIRESTORE_TRANSACTION), get()) }
    }

    val businessModule = module {
        single { TagBusiness(get()) }
        single { TypeBusiness(get()) }
        single { TransactionBusiness(get(), get()) }
        single { HomeBusiness(get(), get(), get()) }
    }

    val appModule = module {
        single { MyUtils() }
    }

    val presenterModule = module {
        single { (view: HomeContract.View) -> HomePresenter(view, get(), get()) as HomeContract.Presenter }
        single { (view: TagManagerContract.View) -> TagManagerPresenter(view, get()) as TagManagerContract.Presenter }
        single { (view: TypeManagerContract.View) -> TypeManagerPresenter(view, get()) as TypeManagerContract.Presenter }
        single { (view: TransactionManagerContract.View) -> TransactionManagerPresenter(view, get(), get(), get(), get()) as TransactionManagerContract.Presenter }
    }

}