package com.example.coffeemachile.screens.machine

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.coffeemachile.R
import com.example.coffeemachile.common.AppGlobal
import com.example.coffeemachile.common.MachineResources
import com.example.coffeemachile.common.Screens
import com.example.coffeemachile.models.Coffee
import com.example.coffeemachile.models.Resources
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Class for data processing in machine screen.
 * */
class MachineVM(private val router: Router) : ViewModel() {
    private val _beans = MutableStateFlow(0)
    private val _milk = MutableStateFlow(0)
    private val _water = MutableStateFlow(0)
    private val _cash = MutableStateFlow(0)
    private val _coffeeMakingStatus = MutableStateFlow("")
    private val _selectedCoffee = MutableStateFlow<Coffee?>(null)
    private val _drinkImage = MutableStateFlow(0)
    private val _fabMakeCoffeeVisibility = MutableStateFlow(View.VISIBLE)
    private val _ivCoffeeVisibility = MutableStateFlow(View.INVISIBLE)
    private val image = R.drawable.coffee_drink

    val beans = _beans.asStateFlow()
    val milk = _milk.asStateFlow()
    val water = _water.asStateFlow()
    val cash = _cash.asStateFlow()
    val coffeeMakingStatus = _coffeeMakingStatus.asStateFlow()
    val selectedCoffee = _selectedCoffee.asStateFlow()
    val drinkImage = _drinkImage.asStateFlow()
    val fabMakeCoffeeVisibility = _fabMakeCoffeeVisibility.asStateFlow()
    val ivCoffeeVisibility = _ivCoffeeVisibility.asStateFlow()

    private val second = 1000L

    /**
     * Then ViewModel init this trigger activate.
     * */
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

    fun addMoney(cash: Int) {
        _cash.value += cash
    }

    fun removeMoney(cash: Int) {
        _cash.value -= cash
    }

    private suspend fun boilWater(water: Int) {
        _water.value -= water
        _coffeeMakingStatus.value = "Boiling water."
        delay(3 * second)
    }

    private suspend fun cookCoffee(beans: Int) {
        _beans.value -= beans
        _coffeeMakingStatus.value = "Melting coffee beans."
        delay(2 * second)
        _coffeeMakingStatus.value = "Boil the ground beans."
        delay(3 * second)
    }

    private suspend fun whipMilk(milk: Int) {
        _milk.value -= milk
        _coffeeMakingStatus.value = "Whip the milk."
        delay(5 * second)
    }

    private suspend fun mixCoffeeAndMilk() {
        _coffeeMakingStatus.value = "Mix coffee and milk."
        delay(3 * second)
    }

    suspend fun makeCoffee() {
        if (_beans.value > 0 && _milk.value > 0 && _water.value > 0) {
            when (_selectedCoffee.value) {
                Coffee.Espresso -> {
                    _fabMakeCoffeeVisibility.value = View.INVISIBLE
                    boilWater(100)
                    cookCoffee(50)
                    whipMilk(30)
                    mixCoffeeAndMilk()
                    setCoffeeGlass()
                }

                Coffee.Cappuccino -> {
                    _fabMakeCoffeeVisibility.value = View.INVISIBLE
                    boilWater(100)
                    cookCoffee(50)
                    whipMilk(25)
                    mixCoffeeAndMilk()
                    setCoffeeGlass()
                }

                null -> {
                    Toast.makeText(
                        AppGlobal.Instance,
                        "You don't peek a coffee.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            _coffeeMakingStatus.value = "Get your coffee."
        } else {
            Toast.makeText(AppGlobal.Instance, "Add resources", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setCoffeeGlass() {
        _drinkImage.value = image
        _ivCoffeeVisibility.value = View.VISIBLE
    }

    fun clearGlass() {
        _drinkImage.value = 0
        _fabMakeCoffeeVisibility.value = View.VISIBLE
        _ivCoffeeVisibility.value = View.INVISIBLE
    }

    fun setCoffee(coffee: Coffee) {
        _selectedCoffee.value = coffee
        _coffeeMakingStatus.value = ""
    }

    fun addResources() = router.navigateTo(Screens.Delivery())
}