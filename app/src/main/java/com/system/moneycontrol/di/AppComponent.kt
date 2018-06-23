package com.system.moneycontrol.di

import com.system.moneycontrol.MyApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(app: MyApplication)

    fun plus(mainActivityModule: MainActivityModule): MainActivityComponent

    fun plus(mainFragmentModule: MainFragmentModule): MainFragmentComponent
}