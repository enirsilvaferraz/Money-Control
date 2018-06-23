package com.system.moneycontrol.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.system.moneycontrol.ui.main.*
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
        fun providePresenter(view: MainContract.View, business: MainContract.Business): MainContract.Presenter = MainPresenter(view, business)

        @JvmStatic
        @Provides
        fun provideBusiness(repository: MainContract.Repository): MainContract.Business = MainBusiness(repository)

        @JvmStatic
        @Provides
        fun provideRepository(collection: CollectionReference): MainContract.Repository = TransactionRepository(collection)

        @JvmStatic
        @Provides
        fun provideFirebaseTransaction(): CollectionReference = FirebaseFirestore.getInstance().collection("transactions")
    }
}