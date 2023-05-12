package com.example.coffeemachile.common

import android.app.Application
import com.example.coffeemachile.common.di.navigationModule
import com.example.coffeemachile.common.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Class for maintaining global application state.
 * Announce DI components([navigationModule] and [vmModule]).
 * */
class AppGlobal : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this

        MachineResources.attachContext(this)

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@AppGlobal)
            modules(
                navigationModule,
                vmModule
            )
        }
    }

    companion object {
        private lateinit var instance: AppGlobal

        val Instance: AppGlobal get() = instance
    }
}