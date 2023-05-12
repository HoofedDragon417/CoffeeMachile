package com.example.coffeemachile.common.di

import com.example.coffeemachile.screens.delivery.DeliveryVM
import com.example.coffeemachile.screens.machine.MachineVM
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * DI module responsible for the application ViewModels.
 * */
val vmModule = module {
    viewModelOf(::MachineVM)
    viewModelOf(::DeliveryVM)
}