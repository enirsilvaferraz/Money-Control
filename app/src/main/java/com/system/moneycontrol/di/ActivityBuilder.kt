package com.system.moneycontrol.di

import android.app.Activity
import com.system.moneycontrol.ui.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMainActivity(builder: MainActivityComponent.Builder): AndroidInjector.Factory<Activity>
}