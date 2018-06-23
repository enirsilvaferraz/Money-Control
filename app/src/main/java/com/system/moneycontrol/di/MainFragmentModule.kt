package com.system.moneycontrol.di

import com.system.moneycontrol.ui.main.MainContract
import com.system.moneycontrol.ui.main.MainFragment
import com.system.moneycontrol.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainFragmentModule(val view: MainFragment) {

    @Provides
    fun provideView(): MainContract.View = view

    @Provides
    fun providePresenter(): MainContract.Presenter = MainPresenter(view)
}