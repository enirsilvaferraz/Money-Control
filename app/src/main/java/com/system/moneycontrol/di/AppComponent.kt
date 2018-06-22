package com.system.moneycontrol.di

import com.system.moneycontrol.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuilder::class)
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: MyApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: MyApplication)
}