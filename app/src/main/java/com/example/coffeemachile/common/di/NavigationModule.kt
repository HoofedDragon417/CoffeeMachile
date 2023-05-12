package com.example.coffeemachile.common.di

import com.github.terrakok.cicerone.Cicerone
import org.koin.dsl.module

private val cicerone = Cicerone.create()
/**
 * DI module responsible for the application navigation components.
 * */
val navigationModule = module {
    single { cicerone }
    single { cicerone.router }
    single { cicerone.getNavigatorHolder() }
}