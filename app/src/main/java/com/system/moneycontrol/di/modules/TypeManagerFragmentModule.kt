package com.system.moneycontrol.di.modules

import com.system.moneycontrol.ui.typemanager.TypeManagerContract
import com.system.moneycontrol.ui.typemanager.TypeManagerFragment
import com.system.moneycontrol.ui.typemanager.TypeManagerPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class TypeManagerFragmentModule {

    @Binds
    abstract fun provideView(view: TypeManagerFragment): TypeManagerContract.View

    @Binds
    abstract fun providePresenter(presenter: TypeManagerPresenter): TypeManagerContract.Presenter
}