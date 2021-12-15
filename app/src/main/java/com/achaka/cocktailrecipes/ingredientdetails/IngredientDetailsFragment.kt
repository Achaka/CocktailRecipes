package com.achaka.cocktailrecipes.ingredientdetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.achaka.cocktailrecipes.CocktailsApp
import com.achaka.cocktailrecipes.R
import com.achaka.cocktailrecipes.State
import com.achaka.cocktailrecipes.databinding.FragmentIngredientDetailsBinding
import com.achaka.cocktailrecipes.model.domain.Ingredient
import com.achaka.cocktailrecipes.model.network.dtos.IngredientResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

private const val INGREDIENT_NAME_ARG = "ingredientName"

class IngredientDetailsFragment : Fragment() {

    private var ingredientName: String? = null

    private var _binding: FragmentIngredientDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: IngredientDetailsViewModel by viewModels {
        IngredientDetailsViewModelFactory((activity?.application as CocktailsApp).ingredientsRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ingredientName = it.getString(INGREDIENT_NAME_ARG)
        }
        ingredientName?.let { viewModel.getIngredientByName(it) }
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
            viewModel.state.onEach { state ->
                when (state) {
                    is State.Success<Ingredient> -> {
                        hideProgress()
                        showIngredientInfo(state.data)
                    }
                    is State.Error -> {
                        hideProgress()
                        Toast.makeText(requireContext(), state.exceptionMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is State.Loading -> {
                        showProgress()
                    }
                }
            }.collect()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun showProgress() {
        binding.progressIndicator.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressIndicator.visibility = View.GONE
    }

    private fun showIngredientInfo(ingredient: Ingredient) {
        binding.nameHeader.text = ingredient.name
        binding.alcoholic.text = getString(R.string.alcoholic, ingredient.alcoholic.type)
        binding.abv.text = getString(R.string.abv, ingredient.ABV)
        binding.description.text = ingredient.description
    }

    companion object {
        @JvmStatic
        fun newInstance(ingredientName: String) =
            IngredientDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(INGREDIENT_NAME_ARG, ingredientName)
                }
            }
    }
}