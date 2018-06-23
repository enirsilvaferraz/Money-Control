package com.system.moneycontrol.di

import com.system.moneycontrol.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: MyApplication) {

    @Provides
    @Singleton
    internal fun provideContext(): MyApplication = application

}