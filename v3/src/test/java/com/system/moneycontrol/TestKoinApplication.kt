package com.system.moneycontrol

import android.app.Application
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest
import org.koin.android.ext.android.startKoin

class TestKoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        FirebaseApp.initializeApp(this)
        initDI()
    }

    fun initDI() {
        startKoin(this, listOf(KoinModuleTest.model, KoinModuleTest.view, KoinModuleTest.business))
    }
}