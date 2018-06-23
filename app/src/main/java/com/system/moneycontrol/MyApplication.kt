package com.system.moneycontrol

import android.app.Application
import com.system.moneycontrol.di.AppComponent
import com.system.moneycontrol.di.AppModule
import com.system.moneycontrol.di.DaggerAppComponent

class MyApplication : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder().build();
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}