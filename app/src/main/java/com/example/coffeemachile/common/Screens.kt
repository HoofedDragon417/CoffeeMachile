package com.example.coffeemachile.common

import com.example.coffeemachile.screens.delivery.DeliveryFragment
import com.example.coffeemachile.screens.machine.MachineFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

/**
 * Object class that contain the application screens.
 * */
object Screens {
    fun Main() = FragmentScreen { MachineFragment() }

    fun Delivery() = FragmentScreen { DeliveryFragment() }
}