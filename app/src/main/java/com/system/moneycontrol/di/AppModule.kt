package com.system.moneycontrol.di

import android.app.Application
import com.system.moneycontrol.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = arrayOf(MainActivityComponent::class))
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: MyApplication) = application

}