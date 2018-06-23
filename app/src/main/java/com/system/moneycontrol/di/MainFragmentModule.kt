package com.system.moneycontrol.di

import com.system.moneycontrol.ui.main.MainContract
import com.system.moneycontrol.ui.main.MainFragment
import com.system.moneycontrol.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
abstract class MainFragmentModule() {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideView(view: MainFragment): MainContract.View = view

        @JvmStatic
        @Provides
        fun providePresenter(view: MainContract.View): MainContract.Presenter = MainPresenter(view)
    }
}