package com.achaka.cocktailrecipes.details

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.cocktailrecipes.CocktailsApp
import com.achaka.cocktailrecipes.R
import com.achaka.cocktailrecipes.databinding.FragmentDrinkDetailsBinding
import com.achaka.cocktailrecipes.ingredientdetails.IngredientDetailsFragment
import com.achaka.cocktailrecipes.model.database.entities.Commentary
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.model.domain.UserDrink
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

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
        viewModel.ifInFavourites(drinkItem)
        viewModel.addToRecent((drinkItem as Drink).id)
        viewModel.getCommentary(drinkItem)

        lifecycleScope.launchWhenStarted {
            viewModel.commentary.onEach{
                if (it != null) {
                    binding.commentaryHeader.visibility = View.VISIBLE
                    binding.commentary.visibility = View.VISIBLE
                    binding.commentary.setText(it.commentary)
                } else {
                    binding.commentaryHeader.visibility = View.GONE
                    binding.commentary.visibility = View.GONE
                    binding.confirmCommentaryButton. visibility = View.GONE
                }
            }.collect()
        }
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
        recyclerView.adapter = adapter

        displayDrink(drinkItem)

        binding.addCommentaryButton.setOnClickListener {
            binding.commentaryHeader.visibility = View.VISIBLE
            binding.commentary.visibility = View.VISIBLE
            binding.commentary.isEnabled = true
            binding.confirmCommentaryButton.visibility = View.VISIBLE
        }

        binding.confirmCommentaryButton.setOnClickListener {
            val commentaryText = binding.commentary.text.toString()
            if (commentaryText.isNotEmpty()) {
                viewModel.addCommentary(Commentary((drinkItem as Drink).id, commentaryText, false))
//            if success(make fun return smth)
                binding.commentary.isEnabled = false
                binding.confirmCommentaryButton.visibility = View.GONE
            } else {
                binding.commentaryHeader.visibility = View.GONE
                binding.commentary.visibility = View.GONE
                binding.confirmCommentaryButton. visibility = View.GONE
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        viewModel.isFavourite.value
        lifecycleScope.launchWhenStarted {
            viewModel.isFavourite.onEach { isFavourite ->
                if (isFavourite) {
                    menu.findItem(R.id.add_to_favourites).icon.setTint(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.crimson
                        )
                    )
                } else {
                    menu.getItem(0).icon.setTint(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                }
            }.collect()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.drink_details_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_to_favourites) {
            if (drinkItem != null) {
                if (!viewModel.isFavourite.value) {
                    viewModel.addToFavourites(drinkItem)
                } else {
                    viewModel.removeFromFavourites(drinkItem)
                }
            }

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
            .replace(
                R.id.main_fragment_container,
                IngredientDetailsFragment.newInstance(ingredientName)
            )
            .addToBackStack("drink_details_to_Ingredient_details").commit()
    }

}

interface OnIngredientClick {
    fun onIngredientClick(ingredientName: String)
}