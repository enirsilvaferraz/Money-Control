package com.system.moneycontrol.di.modules

import android.app.Activity
import com.system.moneycontrol.ui.taglist.TagListActivity
import com.system.moneycontrol.ui.taglist.TagListFragment
import com.system.moneycontrol.ui.tagmanager.TagManagerActivity
import com.system.moneycontrol.ui.tagmanager.TagManagerFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TagListActivityModule {

    @Binds
    abstract fun activity(activity: TagListActivity): Activity

    @ContributesAndroidInjector(modules = [TagListFragmentModule::class])
    abstract fun fragment(): TagListFragment
}