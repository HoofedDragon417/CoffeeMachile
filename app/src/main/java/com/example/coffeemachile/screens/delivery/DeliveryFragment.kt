package com.example.coffeemachile.screens.delivery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.coffeemachile.R
import com.example.coffeemachile.databinding.FragmentDeliveryBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Class for delivery screen.
 * */
class DeliveryFragment : Fragment(R.layout.fragment_delivery) {

    private val binding: FragmentDeliveryBinding by viewBinding(FragmentDeliveryBinding::bind)

    private val viewModel: DeliveryVM by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscription()

        binding.fabPlusResources.setOnClickListener {

            val milk = binding.etMilk.text.toIntOrZero()

            val water = binding.etWater.text.toIntOrZero()

            val beans = binding.etBeans.text.toIntOrZero()

            viewModel.addResources(
                milk = milk,
                water = water,
                beans = beans
            )
        }

        binding.fabMinusResources.setOnClickListener {

            val milk = binding.etMilk.text.toIntOrZero()

            val water = binding.etWater.text.toIntOrZero()

            val beans = binding.etBeans.text.toIntOrZero()

            viewModel.removeResources(
                milk = milk,
                water = water,
                beans = beans
            )
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveResources()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMachine()
    }

    private fun subscription() {
        viewModel.beans.onEach { beans ->
            binding.tvBeans.text = resources.getString(R.string.beans, beans)
        }.launchIn(lifecycleScope)

        viewModel.milk.onEach { milk ->
            binding.tvMilk.text = resources.getString(R.string.milk, milk)
        }.launchIn(lifecycleScope)

        viewModel.water.onEach { water ->
            binding.tvWater.text = resources.getString(R.string.water, water)
        }.launchIn(lifecycleScope)

        viewModel.cash.onEach { cash ->
            binding.tvCash.text = resources.getString(R.string.cash, cash)
        }.launchIn(lifecycleScope)
    }

    private fun CharSequence?.toIntOrZero(): Int {
        return if (this?.toString()?.isNotEmpty()!!) this.toString().toInt() else 0
    }
}