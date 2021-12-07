package com.achaka.cocktailrecipes.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.cocktailrecipes.CocktailsApp
import com.achaka.cocktailrecipes.MainRecyclerViewAdapter
import com.achaka.cocktailrecipes.R
import com.achaka.cocktailrecipes.databinding.FragmentFavouritesBinding
import com.achaka.cocktailrecipes.details.DrinkDetailsFragment
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.search.OnItemClick
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class FavouritesFragment : Fragment(), OnItemClick {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MainRecyclerViewAdapter

    private val viewModel: FavouritesViewModel by viewModels {
        val cocktailsApp = activity?.application as CocktailsApp
        FavouritesViewModelFactory(cocktailsApp.drinkRepository, cocktailsApp.userDrinkRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val glide = Glide.with(this)
        adapter = MainRecyclerViewAdapter(glide, this)

        lifecycleScope.launchWhenStarted {
            viewModel.favouriteDrinks.onEach {
                adapter.submitList(it)
            }.collect()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        val layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FavouritesFragment().apply {
            }
    }

    override fun openDetails(drink: DrinkItem) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, DrinkDetailsFragment.newInstance(drink))
            .addToBackStack("search_to_details").commit()
    }
}
