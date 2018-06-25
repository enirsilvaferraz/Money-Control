package com.system.moneycontrol.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.repositories.TransactionRepository
import com.system.moneycontrol.ui.home.HomeContract
import com.system.moneycontrol.ui.home.HomeFragment
import com.system.moneycontrol.ui.home.HomePresenter
import dagger.Module
import dagger.Provides

@Module
class HomeFragmentModule() {

    @Provides
    fun provideView(view: HomeFragment): HomeContract.View = view

    @Provides
    fun providePresenter(view: HomeContract.View, business: HomeBusiness): HomeContract.Presenter = HomePresenter(view, business)

    @Provides
    fun provideFirebaseTransaction(): CollectionReference = FirebaseFirestore.getInstance().collection("transactions")
}