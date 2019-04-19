package com.system.moneycontrol.v3.infrastructure

import com.system.moneycontrol.v3.business.AccountBusiness
import com.system.moneycontrol.v3.business.TagBusiness
import com.system.moneycontrol.v3.business.TransactionBusiness
import com.system.moneycontrol.v3.data.Account
import com.system.moneycontrol.v3.data.Tag
import com.system.moneycontrol.v3.data.Transaction
import com.system.moneycontrol.v3.presenter.transaction.TransactionListContract
import com.system.moneycontrol.v3.presenter.transaction.TransactionListPresenter
import com.system.moneycontrol.v3.repositories.*
import org.koin.dsl.module.module

object KoinModule {

    val firebaseModule = module {
        single("COLL_TRANS") { FirebaseFactory.instance().collection(Transaction::class.simpleName!!) }
        single("COLL_TAG") { FirebaseFactory.instance().collection(Tag::class.simpleName!!) }
        single("COLL_ACCOUNT") { FirebaseFactory.instance().collection(Account::class.simpleName!!) }
    }

    val repositoryModule = module {
        factory<TransactionRepository> { TransactionRepositoryImpl(collection = get("COLL_TRANSACT")) }
        factory<TagRepository> { TagRepositoryImpl(collection = get("COLL_TAG")) }
        factory<AccountRepository> { AccountRepositoryImpl(collection = get("COLL_ACCOUNT")) }
    }

    val business = module {
        factory { AccountBusiness(repository = get()) }
        factory { TagBusiness(repository = get()) }
        factory { TransactionBusiness(transactionRepository = get(), tagRepository = get(), accountRepository = get()) }
    }

    val presenterModule = module {
        factory { (view: TransactionListContract.View) ->
            TransactionListPresenter(view = view, business = get()) as TransactionListContract.Presenter
        }
    }
}