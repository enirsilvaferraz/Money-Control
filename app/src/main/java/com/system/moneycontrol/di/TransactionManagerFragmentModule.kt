package com.system.moneycontrol.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.system.moneycontrol.model.repositories.TransactionRepository
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerBusiness
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerContract
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerFragment
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerPresenter
import dagger.Module
import dagger.Provides

@Module
open class TransactionManagerFragmentModule {

    @Provides
    open fun provideView(view: TransactionManagerFragment): TransactionManagerContract.View = view

    @Provides
    open fun providePresenter(view: TransactionManagerContract.View, business: TransactionManagerContract.Business): TransactionManagerContract.Presenter = TransactionManagerPresenter(view, business)

    @Provides
    open fun provideBusiness(repository: TransactionRepository): TransactionManagerContract.Business = TransactionManagerBusiness(repository)

    @Provides
    open fun provideRepository(collection: CollectionReference): TransactionRepository = TransactionRepository(collection)

    @Provides
    open fun provideFirebaseTransaction(): CollectionReference = FirebaseFirestore.getInstance().collection("transactions")
}