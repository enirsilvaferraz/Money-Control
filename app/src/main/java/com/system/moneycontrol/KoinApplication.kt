package com.system.moneycontrol

import android.app.Application
import com.system.moneycontrol.infrastructure.KoinModule
import org.koin.android.ext.android.startKoin

open class KoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    protected open fun initDI() {
        startKoin(this, listOf(
                KoinModule.firebaseModule,
                KoinModule.repositoryModule,
                KoinModule.business,
                KoinModule.presenter
        ))
    }

}