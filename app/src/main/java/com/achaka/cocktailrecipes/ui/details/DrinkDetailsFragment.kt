package com.achaka.cocktailrecipes.ui.details

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.cocktailrecipes.R
import com.achaka.cocktailrecipes.databinding.FragmentDrinkDetailsBinding
import com.achaka.cocktailrecipes.ui.ingredientdetails.IngredientDetailsFragment
import com.achaka.cocktailrecipes.data.database.entities.Commentary
import com.achaka.cocktailrecipes.domain.model.Drink
import com.achaka.cocktailrecipes.domain.model.DrinkItem
import com.achaka.cocktailrecipes.domain.model.IngredientMeasureItem
import com.achaka.cocktailrecipes.ui.shoppinglist.ShoppingListFragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

private const val DRINK_ARG = "drink"

@AndroidEntryPoint
class DrinkDetailsFragment : Fragment(), OnIngredientClick {

    private var drinkItem: DrinkItem? = null

    private var _binding: FragmentDrinkDetailsBinding? = null
    private val binding get() = _binding!!
    private val adapter = IngredientMeasuresRecyclerViewAdapter(this)

    private var initialMeasuresList = listOf<IngredientMeasureItem>()

    private val viewModel: DrinkDetailsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            drinkItem = it.getParcelable(DRINK_ARG)
        }
        viewModel.ifInFavourites(drinkItem)
        viewModel.getCommentary(drinkItem)
        viewModel.addToRecents(drinkItem as Drink)

        initialMeasuresList = (drinkItem as Drink).ingredientMeasureItems
        loadCommentary()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        activity?.title = (drinkItem as Drink).name

        _binding =
            FragmentDrinkDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupIngredientsRecyclerView()
        displayDrink(drinkItem)
        setupCounter()

        binding.addCommentaryButton.setOnClickListener {
            showCommentary()
            binding.commentary.isEnabled = true
            binding.confirmCommentaryButton.visibility = View.VISIBLE
        }

        binding.confirmCommentaryButton.setOnClickListener {
            val commentaryText = binding.commentary.text.toString()
            if (commentaryText.isNotEmpty()) {
                addCommentary(commentaryText)
                binding.commentary.isEnabled = false
                binding.confirmCommentaryButton.visibility = View.GONE
            } else {
                hideCommentary()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setupIngredientsRecyclerView() {
        val recyclerView = binding.ingredientsRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = null
    }

    private fun setupCounter() {
        binding.measuresCounter.text = getString(R.string.default_counter_value)
        var currentValue = binding.measuresCounter.text.toString().toInt()

        binding.plusButton.setOnClickListener {
            if (currentValue <= 9) {
                currentValue++
                binding.measuresCounter.text = currentValue.toString()
                updateAdapter(currentValue)
            }
        }

        binding.minusButton.setOnClickListener {
            if (currentValue >= 2)
                currentValue--
            binding.measuresCounter.text = currentValue.toString()
            updateAdapter(currentValue)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

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
        when (item.itemId) {
            R.id.add_to_favourites -> {
                if (drinkItem != null) {
                    if (!viewModel.isFavourite.value) {
                        viewModel.addToFavourites(drinkItem)
                    } else {
                        viewModel.removeFromFavourites(drinkItem)
                    }
                }
            }
            R.id.move_to_shopping_list -> {
                if (drinkItem != null) {
                    viewModel.moveToShoppingList(adapter.currentList)
                    openShoppingListFragment()
                }
            }
        }
        return true
    }

    private fun openShoppingListFragment() {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                ShoppingListFragment.newInstance(),
                "SHOPPING_LIST"
            ).addToBackStack("details_to_shopping_list")
            .commit()
    }

    private fun displayDrink(drink: DrinkItem?) {
        if (drink is Drink) {
            binding.nameHeader.text = drink.name
            binding.instructions.text = drink.instructions
            Glide.with(this).load(drink.thumbUrl).into(binding.image)
            adapter.submitList(drink.ingredientMeasureItems)
        }
//        if (drink is UserDrink) {
//            binding.nameHeader.text = drink.name
//            binding.instructions.text = drink.instructions
//            adapter.submitList(drink.ingredientMeasureItems)
//        }
    }

    private fun addCommentary(commentaryText: String) {
        viewModel.addCommentary(Commentary((drinkItem as Drink).id, commentaryText, false))
    }

    private fun hideCommentary() {
        binding.commentaryHeader.visibility = View.GONE
        binding.commentary.visibility = View.GONE
        binding.confirmCommentaryButton.visibility = View.GONE
    }

    private fun showCommentary() {
        binding.commentaryHeader.visibility = View.VISIBLE
        binding.commentary.visibility = View.VISIBLE
    }

    private fun updateAdapter(multiplier: Int) {
        val measuresList = mutableListOf<IngredientMeasureItem>()

        initialMeasuresList.forEach { measuresList.add(it.copy()) }

        println(measuresList[0] === initialMeasuresList[0])

        measuresList.map { item ->
            val measure = item.measure
            if (measure != null) {
                item.measure = measure * multiplier
            }
            val range = item.range
            if (range != null) {
                item.range = Pair(range.first * multiplier, range.second * multiplier)
            }
        }
        adapter.submitList(measuresList)
        println(measuresList[0] === initialMeasuresList[0])
    }

    private fun loadCommentary() {
        lifecycleScope.launchWhenStarted {
            viewModel.commentary.onEach {
                if (it != null) {
                    binding.commentary.setText(it.commentary)
                    showCommentary()
                } else {
                    hideCommentary()
                }
            }.collect()
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