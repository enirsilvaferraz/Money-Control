package com.system.moneycontrol.di.modules

import android.app.Activity
import com.system.moneycontrol.ui.tagmanager.TagManagerActivity
import com.system.moneycontrol.ui.tagmanager.TagManagerFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TagManagerActivityModule {

    @Binds
    abstract fun activity(activity: TagManagerActivity): Activity

    @ContributesAndroidInjector(modules = [TagManagerFragmentModule::class])
    abstract fun fragment(): TagManagerFragment
}