package com.example.coffeemachile.screens.machine

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.coffeemachile.R
import com.example.coffeemachile.databinding.FragmentMachineBinding
import com.example.coffeemachile.models.Coffee
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Class for machine screen.
 * */
class MachineFragment : Fragment(R.layout.fragment_machine) {

    private val binding: FragmentMachineBinding by viewBinding(FragmentMachineBinding::bind)

    private val viewModel: MachineVM by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscription()

        binding.fabAddResources.setOnClickListener {
            viewModel.addResources()
        }

        binding.fabAddMoney.setOnClickListener {
            val cash =
                if (binding.etCash.text.isNullOrEmpty()) 0 else binding.etCash.text.toString()
                    .toInt()
            binding.etCash.text = null
            viewModel.addMoney(cash)
        }

        binding.fabRemoveMoney.setOnClickListener {
            val cash =
                if (binding.etCash.text.isNullOrEmpty()) 0 else binding.etCash.text.toString()
                    .toInt()
            binding.etCash.text = null
            viewModel.removeMoney(cash)
        }

        binding.fabMakeCoffee.setOnClickListener {
            lifecycleScope.launch {
                viewModel.makeCoffee()
            }
        }

        binding.rbCappuccino.setOnClickListener {
            viewModel.setCoffee(Coffee.Cappuccino)
        }

        binding.rbEspresso.setOnClickListener {
            viewModel.setCoffee(Coffee.Espresso)
        }

        binding.ivCoffee.setOnClickListener {
            viewModel.clearGlass()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMachine()
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveResources()
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

        viewModel.coffeeMakingStatus.onEach { status ->
            binding.tvCoffeeStatus.text = status
        }.launchIn(lifecycleScope)

        viewModel.selectedCoffee.onEach { coffee ->
            when (coffee) {
                Coffee.Cappuccino -> {
                    binding.rbCappuccino.isChecked = true
                    binding.rbEspresso.isChecked = false
                }

                Coffee.Espresso -> {
                    binding.rbEspresso.isChecked = true
                    binding.rbCappuccino.isChecked = false
                }

                null -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.drinkImage.onEach {
            binding.ivCoffee.setImageResource(it)
        }.launchIn(lifecycleScope)

        viewModel.fabMakeCoffeeVisibility.onEach { visible ->
            binding.fabMakeCoffee.visibility = visible
        }.launchIn(lifecycleScope)

        viewModel.ivCoffeeVisibility.onEach { visible ->
            binding.ivCoffee.visibility = visible
        }.launchIn(lifecycleScope)
    }
}