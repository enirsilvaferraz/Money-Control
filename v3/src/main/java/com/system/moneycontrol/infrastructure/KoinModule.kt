package com.system.moneycontrol.infrastructure

import com.system.moneycontrol.business.AccountBusiness
import com.system.moneycontrol.business.TagBusiness
import com.system.moneycontrol.business.TransactionBusiness
import com.system.moneycontrol.data.Account
import com.system.moneycontrol.data.Tag
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.repositories.AccountRepository
import com.system.moneycontrol.repositories.TagRepository
import com.system.moneycontrol.repositories.TransactionRepository
import com.system.moneycontrol.repositories.firebase.AccountRepositoryImpl
import com.system.moneycontrol.repositories.firebase.TagRepositoryImpl
import com.system.moneycontrol.repositories.firebase.TransactionRepositoryImpl
import com.system.moneycontrol.view.transaction.TransactionListContract
import com.system.moneycontrol.view.transaction.TransactionListPresenter
import com.system.moneycontrol.view.transaction.TransactionManagerContract
import com.system.moneycontrol.view.transaction.TransactionManagerPresenter
import org.koin.dsl.module.module

object KoinModule {

    val firebaseModule = module {
        single("COLL_TRANS") { FirebaseFactory.instance().collection(Transaction::class.simpleName!!) }
        single("COLL_TAG") { FirebaseFactory.instance().collection(Tag::class.simpleName!!) }
        single("COLL_ACCOUNT") { FirebaseFactory.instance().collection(Account::class.simpleName!!) }
    }

    val repositoryModule = module {
        factory<TransactionRepository> { TransactionRepositoryImpl(collection = get("COLL_TRANS")) }
        factory<TagRepository> { TagRepositoryImpl(collection = get("COLL_TAG")) }
        factory<AccountRepository> { AccountRepositoryImpl(collection = get("COLL_ACCOUNT")) }
    }

    val business = module {
        factory { AccountBusiness(repository = get()) }
        factory { TagBusiness(repository = get()) }
        factory { TransactionBusiness(transactionRepository = get(), tagRepository = get(), accountRepository = get()) }
    }

    val presenter = module {

        factory<TransactionListContract.Presenter> { (view: TransactionListContract.View) ->
            TransactionListPresenter(view = view, business = get())
        }

        factory<TransactionManagerContract.Presenter> { (view: TransactionManagerContract.View) ->
            TransactionManagerPresenter(view = view, transacBusiness = get(), tagBusiness = get(), accountBusiness = get())
        }
    }
}