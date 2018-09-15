package com.system.moneycontrol.di.modules

import android.app.Activity
import com.system.moneycontrol.ui.tag.TagListActivity
import com.system.moneycontrol.ui.tag.TagListFragment
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