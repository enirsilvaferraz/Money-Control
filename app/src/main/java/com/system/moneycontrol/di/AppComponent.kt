package com.system.moneycontrol.di

import android.app.Application
import com.system.moneycontrol.MyApplication
import com.system.moneycontrol.di.modules.FirebaseFirestoreModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
//    FragmentsBindingModule::class,
    ActivitiesBindingModule::class,
    FirebaseFirestoreModule::class
])

interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun create(app: Application): Builder

        fun build(): AppComponent
    }
}