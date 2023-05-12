package com.example.coffeemachile.common

import android.content.Context
import com.example.coffeemachile.models.Resources
import kotlin.properties.Delegates

/**
 * Object class that store machine resources in cash.
 * */
object MachineResources {

    private const val PREF_NAME = "machine"

    private const val MILK = "milk"
    private const val WATER = "water"
    private const val BEANS = "beans"
    private const val CASH = "cash"

    private var context: Context by Delegates.notNull()

    private val data by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun attachContext(context: Context) {
        this.context = context
    }

    fun saveResources(resources: Resources) {
        data.edit().putInt(MILK, resources.milk).apply()
        data.edit().putInt(WATER, resources.water).apply()
        data.edit().putInt(BEANS, resources.beans).apply()
        data.edit().putInt(CASH, resources.cash).apply()
    }

    fun getResources() = Resources(
        data.getInt(MILK, 0),
        data.getInt(WATER, 0),
        data.getInt(BEANS, 0),
        data.getInt(CASH, 0)
    )
}