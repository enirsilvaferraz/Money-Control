package com.system.moneycontrol.di.modules

import com.system.moneycontrol.ui.tagmanager.TagManagerContract
import com.system.moneycontrol.ui.tagmanager.TagManagerFragment
import com.system.moneycontrol.ui.tagmanager.TagManagerPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class TagManagerFragmentModule {

    @Binds
    abstract fun provideView(view: TagManagerFragment): TagManagerContract.View

    @Binds
    abstract fun providePresenter(presenter: TagManagerPresenter): TagManagerContract.Presenter
}