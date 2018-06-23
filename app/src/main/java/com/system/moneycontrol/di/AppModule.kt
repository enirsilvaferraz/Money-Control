package com.system.moneycontrol.di

import com.system.moneycontrol.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: MyApplication): MyApplication = application

}