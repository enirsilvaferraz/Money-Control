package com.system.moneycontrol.v3.infrastructure

import com.system.moneycontrol.v3.business.TransactionBusiness
import com.system.moneycontrol.v3.presenter.transaction.TransactionListContract
import com.system.moneycontrol.v3.presenter.transaction.TransactionListPresenter
import com.system.moneycontrol.v3.repositories.TransactionRepository
import com.system.moneycontrol.v3.repositories.TransactionRepositoryImpl
import org.koin.dsl.module.module

object KoinModule {

    val firebaseModule = module {
        single("COLL_TRANS") { FirebaseFactory.instance().collection("transactions") }
    }

    val repositoryModule = module {
        factory { TransactionRepositoryImpl(collection = get("COLL_TRANS")) as TransactionRepository }
    }

    val businessModule = module {
        factory { TransactionBusiness(expRep = get()) }
    }

    val presenterModule = module {
        factory { (view: TransactionListContract.View) ->
            TransactionListPresenter(view = view, business = get()) as TransactionListContract.Presenter
        }
    }
}