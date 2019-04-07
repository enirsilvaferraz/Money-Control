package com.system.moneycontrol

import com.google.firebase.FirebaseApp
import com.system.moneycontrol.infrastructure.koin.KoinModules.presenterModule
import com.system.moneycontrol.infrastructure.koin.TestKoinModules.appModuleMock
import com.system.moneycontrol.infrastructure.koin.TestKoinModules.businessModuleMock
import com.system.moneycontrol.v3.infrastructure.KoinModule
import org.koin.android.ext.android.startKoin

class TestKoinApplication : KoinApplication() {

    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }

    override fun initDI() {
        startKoin(this, listOf(KoinModule.presenterMockModule, appModuleMock, businessModuleMock, presenterModule))
    }
}