package com.achaka.cocktailrecipes.details

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.cocktailrecipes.CocktailsApp
import com.achaka.cocktailrecipes.R
import com.achaka.cocktailrecipes.databinding.FragmentDrinkDetailsBinding
import com.achaka.cocktailrecipes.ingredientdetails.IngredientDetailsFragment
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.model.domain.UserDrink
import com.bumptech.glide.Glide

private const val DRINK_ARG = "drink"

class DrinkDetailsFragment : Fragment(), OnIngredientClick {

    private var drinkItem: DrinkItem? = null

    private var _binding: FragmentDrinkDetailsBinding? = null
    private val binding get() = _binding!!
    private val adapter = IngredientMeasuresRecyclerViewAdapter(this)

    private val viewModel: DrinkDetailsViewModel by viewModels {
        DetailsViewModelFactory((activity?.application as CocktailsApp).drinkRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            drinkItem = it.getParcelable(DRINK_ARG)
        }
        viewModel.addToRecent((drinkItem as Drink).id)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentDrinkDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.ingredientsRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        displayDrink(drinkItem)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.drink_details_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_to_favourites) {
            if (drinkItem != null)
                viewModel.addToFavourites(drinkItem)
        }
        return true
    }

    private fun displayDrink(drink: DrinkItem?) {
        if (drink is Drink) {
            binding.nameHeader.text = drink.name
            binding.instructions.text = drink.instructions
            Glide.with(this).load(drink.thumbUrl).into(binding.image)
            adapter.submitList(drink.ingredientMeasureItems)
        }
        if (drink is UserDrink) {
            binding.nameHeader.text = drink.name
            binding.instructions.text = drink.instructions
//            Glide.with(this).load(drink?.thumbUrl).into(binding.image)
            adapter.submitList(drink.ingredientMeasureItems)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(drink: DrinkItem) =
            DrinkDetailsFragment().apply {
                arguments = Bundle().apply {
                    if (drink is Drink) {
                        putParcelable(DRINK_ARG, drink)
                    }
//                    if (drink is UserDrink) {
//                        putParcelable(DRINK_ARG, drink)
//                    }
                }
            }
    }

    override fun onIngredientClick(ingredientName: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, IngredientDetailsFragment.newInstance(ingredientName))
            .addToBackStack("drink_details_to_Ingredient_details").commit()
    }
}

interface OnIngredientClick {
    fun onIngredientClick(ingredientName: String)
}