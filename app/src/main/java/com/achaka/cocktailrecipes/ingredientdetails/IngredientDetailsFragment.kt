package com.achaka.cocktailrecipes.ingredientdetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.achaka.cocktailrecipes.CocktailsApp
import com.achaka.cocktailrecipes.R
import com.achaka.cocktailrecipes.databinding.FragmentIngredientDetailsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class IngredientDetailsFragment : Fragment() {

    private var _binding: FragmentIngredientDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: IngredientDetailsViewModel by viewModels {
        IngredientDetailsViewModelFactory((activity?.application as CocktailsApp).ingredientsRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getIngredientByName("vodka")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.ingredient.onEach {
                if (it != null) {
                    Log.d("aaa", it.description)
                    binding.nameHeader.text = it.name
                    binding.alcoholic.text = getString(R.string.alcoholic, it.alcoholic.type)
                    binding.abv.text = getString(R.string.abv, it.ABV)
                    binding.description.text = it.description
                }
            }.collect()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            IngredientDetailsFragment().apply {

            }
    }
}