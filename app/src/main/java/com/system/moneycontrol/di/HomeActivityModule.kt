package com.system.moneycontrol.di

import android.app.Activity
import com.system.moneycontrol.ui.home.HomeActivity
import com.system.moneycontrol.ui.home.HomeFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeActivityModule {

    @Binds
    abstract fun activity(activity: HomeActivity): Activity

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun fragment(): HomeFragment
}