package com.system.moneycontrol.di

import com.system.moneycontrol.ui.home.HomeActivity
import com.system.moneycontrol.ui.home.HomeFragment
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerActivity
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [TransactionManagerFragmentModule::class])
    abstract fun transactionManagerFragment(): TransactionManagerFragment
}