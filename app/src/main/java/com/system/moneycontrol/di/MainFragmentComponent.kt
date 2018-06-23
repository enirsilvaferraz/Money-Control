package com.system.moneycontrol.di

import com.system.moneycontrol.ui.main.MainFragment
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(MainFragmentModule::class))
interface MainFragmentComponent {

    fun inject(fragment: MainFragment)
}