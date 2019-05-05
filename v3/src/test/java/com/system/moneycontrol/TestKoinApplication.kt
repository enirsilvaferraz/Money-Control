package com.system.moneycontrol

import android.app.Application
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.business
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.model
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.presenter
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.view
import org.koin.android.ext.android.startKoin

class TestKoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        FirebaseApp.initializeApp(this)
        initDI()
    }

    fun initDI() {
        startKoin(this, listOf(model, view, presenter, business))
    }
}