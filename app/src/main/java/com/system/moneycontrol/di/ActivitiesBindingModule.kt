package com.system.moneycontrol.di

import com.system.moneycontrol.di.modules.HomeActivityModule
import com.system.moneycontrol.di.modules.TagManagerActivityModule
import com.system.moneycontrol.di.modules.TransactionManagerActivityModule
import com.system.moneycontrol.di.modules.TypeManagerActivityModule
import com.system.moneycontrol.ui.home.HomeActivity
import com.system.moneycontrol.ui.tag.TagManagerActivity
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerActivity
import com.system.moneycontrol.ui.typemanager.TypeManagerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {

    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun home(): HomeActivity

    @ContributesAndroidInjector(modules = [TransactionManagerActivityModule::class])
    abstract fun transactionManager(): TransactionManagerActivity

    @ContributesAndroidInjector(modules = [TagManagerActivityModule::class])
    abstract fun tagManager(): TagManagerActivity

    @ContributesAndroidInjector(modules = [TypeManagerActivityModule::class])
    abstract fun typeManager(): TypeManagerActivity

}