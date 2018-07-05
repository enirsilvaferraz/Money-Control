package com.system.moneycontrol.di.modules

import com.system.moneycontrol.ui.taglist.TagListContract
import com.system.moneycontrol.ui.taglist.TagListFragment
import com.system.moneycontrol.ui.taglist.TagListPresenter
import com.system.moneycontrol.ui.tagmanager.TagManagerContract
import com.system.moneycontrol.ui.tagmanager.TagManagerFragment
import com.system.moneycontrol.ui.tagmanager.TagManagerPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class TagListFragmentModule {

    @Binds
    abstract fun provideView(view: TagListFragment): TagListContract.View

    @Binds
    abstract fun providePresenter(presenter: TagListPresenter): TagListContract.Presenter
}