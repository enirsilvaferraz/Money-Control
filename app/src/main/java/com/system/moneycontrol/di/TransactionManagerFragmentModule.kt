package com.system.moneycontrol.di

import com.system.moneycontrol.ui.transactionmanager.TransactionManagerContract
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerFragment
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class TransactionManagerFragmentModule {

    @Binds
    abstract fun provideView(view: TransactionManagerFragment): TransactionManagerContract.View

    @Binds
    abstract fun providePresenter(presenter: TransactionManagerPresenter): TransactionManagerContract.Presenter
}