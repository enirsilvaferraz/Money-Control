package com.system.moneycontrol.v3

import android.app.Application
import com.system.moneycontrol.v3.infrastructure.koin.KoinModuleTest
import org.koin.android.ext.android.startKoin

class TestKoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        FirebaseApp.initializeApp(this)
        initDI()
    }

    fun initDI() {
        startKoin(this, listOf(KoinModuleTest.presenter))
    }
}