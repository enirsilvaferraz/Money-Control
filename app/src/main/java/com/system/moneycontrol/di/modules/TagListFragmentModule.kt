package com.system.moneycontrol.di.modules

import com.system.moneycontrol.ui.tag.TagListContract
import com.system.moneycontrol.ui.tag.TagListFragment
import dagger.Binds
import dagger.Module

@Module
abstract class TagListFragmentModule {

    @Binds
    abstract fun provideView(view: TagListFragment): TagListContract.View

//    @Binds
//    abstract fun providePresenter(presenter: HomePresenter): HomeContract.Presenter
}