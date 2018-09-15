package com.system.moneycontrol.di

import com.system.moneycontrol.di.modules.HomeActivityModule
import com.system.moneycontrol.di.modules.TagListActivityModule
import com.system.moneycontrol.di.modules.TransactionManagerActivityModule
import com.system.moneycontrol.ui.home.HomeActivity
import com.system.moneycontrol.ui.tag.TagListActivity
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {

    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun home(): HomeActivity

    @ContributesAndroidInjector(modules = [TransactionManagerActivityModule::class])
    abstract fun transactionManager(): TransactionManagerActivity

    @ContributesAndroidInjector(modules = [TagListActivityModule::class])
    abstract fun tagList(): TagListActivity

}