package com.system.moneycontrol.di.modules

import android.app.Activity
import com.system.moneycontrol.ui.typemanager.TypeManagerActivity
import com.system.moneycontrol.ui.typemanager.TypeManagerFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TypeManagerActivityModule {

    @Binds
    abstract fun activity(activity: TypeManagerActivity): Activity

    @ContributesAndroidInjector(modules = [TypeManagerFragmentModule::class])
    abstract fun fragment(): TypeManagerFragment
}