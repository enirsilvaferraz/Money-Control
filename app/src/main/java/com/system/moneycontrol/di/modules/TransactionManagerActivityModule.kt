package com.system.moneycontrol.di.modules

import android.app.Activity
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerActivity
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TransactionManagerActivityModule {

    @Binds
    abstract fun activity(activity: TransactionManagerActivity): Activity

    @ContributesAndroidInjector(modules = [TransactionManagerFragmentModule::class])
    abstract fun fragment(): TransactionManagerFragment
}