package com.system.moneycontrol.di

import com.system.moneycontrol.ui.main.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent {

    fun inject(activity: MainActivity)
}