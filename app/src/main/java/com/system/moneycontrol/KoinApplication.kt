package com.system.moneycontrol

import android.app.Application
import com.system.moneycontrol.koin.KoinModules.appModule
import com.system.moneycontrol.koin.KoinModules.businessModule
import com.system.moneycontrol.koin.KoinModules.firebaseModule
import com.system.moneycontrol.koin.KoinModules.presenterModule
import com.system.moneycontrol.koin.KoinModules.repositoryModule
import org.koin.android.ext.android.startKoin

class KoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(
                appModule,
                firebaseModule,
                repositoryModule,
                businessModule,
                presenterModule))
    }

}