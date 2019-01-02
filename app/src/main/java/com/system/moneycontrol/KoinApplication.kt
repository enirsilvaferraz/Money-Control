package com.system.moneycontrol

import android.app.Application
import com.system.moneycontrol.infrastructure.koin.KoinModules.businessModule
import com.system.moneycontrol.infrastructure.koin.KoinModules.firebaseModule
import com.system.moneycontrol.infrastructure.koin.KoinModules.presenterModule
import com.system.moneycontrol.infrastructure.koin.KoinModules.repositoryModule
import org.koin.android.ext.android.startKoin

open class KoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    protected open fun initDI() {
        startKoin(this, listOf(firebaseModule, repositoryModule, businessModule, presenterModule))
    }

}