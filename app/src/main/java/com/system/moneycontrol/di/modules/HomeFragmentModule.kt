package com.system.moneycontrol.di.modules

import com.system.moneycontrol.ui.home.HomeContract
import com.system.moneycontrol.ui.home.HomeFragment
import com.system.moneycontrol.ui.home.HomePresenter
import dagger.Binds
import dagger.Module

@Module
abstract class HomeFragmentModule {

    @Binds
    abstract fun provideView(view: HomeFragment): HomeContract.View

    @Binds
    abstract fun providePresenter(presenter: HomePresenter): HomeContract.Presenter
}