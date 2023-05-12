package com.example.coffeemachile.screens.delivery

import androidx.lifecycle.ViewModel
import com.example.coffeemachile.common.MachineResources
import com.example.coffeemachile.models.Resources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Class for data processing in delivery screen.
 * */
class DeliveryVM : ViewModel() {

    private val _beans = MutableStateFlow(0)
    private val _milk = MutableStateFlow(0)
    private val _water = MutableStateFlow(0)
    private val _cash = MutableStateFlow(0)

    val beans = _beans.asStateFlow()
    val milk = _milk.asStateFlow()
    val water = _water.asStateFlow()
    val cash = _cash.asStateFlow()

    init {
        getMachine()
    }

    fun getMachine() {
        val machine = MachineResources.getResources()

        _beans.value = machine.beans
        _milk.value = machine.milk
        _water.value = machine.water
        _cash.value = machine.cash
    }

    fun addResources(milk: Int, water: Int, beans: Int) {
        _beans.value += beans
        _milk.value += milk
        _water.value += water
    }

    fun removeResources(milk: Int, water: Int, beans: Int) {
        _beans.value -= beans
        _milk.value -= milk
        _water.value -= water
    }

    fun saveResources() {
        MachineResources.saveResources(
            Resources(
                milk = _milk.value,
                water = _water.value,
                beans = _beans.value,
                cash = _cash.value
            )
        )
    }

}