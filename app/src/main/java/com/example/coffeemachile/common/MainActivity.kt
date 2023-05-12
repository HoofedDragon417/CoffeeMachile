package com.example.coffeemachile.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeemachile.R
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject
/**
 * Application start point.
 * */
class MainActivity : AppCompatActivity() {
    private val navigator = object : AppNavigator(this, R.id.root_fragment_container) {}

    private val navigatorHolder: NavigatorHolder by inject()
    private val router: Router by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router.newRootScreen(Screens.Main())
    }

    override fun onResumeFragments() {
        navigatorHolder.setNavigator(navigator)
        super.onResumeFragments()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}