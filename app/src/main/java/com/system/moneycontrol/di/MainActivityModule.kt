package com.system.moneycontrol.di

import com.system.moneycontrol.ui.main.MainContract
import com.system.moneycontrol.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

//    @Provides
//    fun providesMainActivity(mainActivity: MainActivity) = mainActivity

    @Provides
    public fun providesMainPresenter(view: MainContract.View): MainContract.Presenter {
        return MainPresenter(view)
    }
}