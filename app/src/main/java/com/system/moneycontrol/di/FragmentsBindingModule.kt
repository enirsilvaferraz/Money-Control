package com.system.moneycontrol.di

import com.system.moneycontrol.di.modules.HomeFragmentModule
import com.system.moneycontrol.di.modules.TransactionManagerFragmentModule
import com.system.moneycontrol.ui.home.HomeFragment
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBindingModule {

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun home(): HomeFragment

    @ContributesAndroidInjector(modules = [TransactionManagerFragmentModule::class])
    abstract fun transactionManager(): TransactionManagerFragment
}