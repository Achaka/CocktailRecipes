package com.achaka.cocktailrecipes.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.cocktailrecipes.CocktailsApp
import com.achaka.cocktailrecipes.R
import com.achaka.cocktailrecipes.databinding.FragmentSearchBinding
import com.achaka.cocktailrecipes.details.DrinkDetailsFragment
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchFragment : Fragment(), OnItemClick {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var randomStripAdapter: SearchHorizontalAdapter
    private lateinit var popularStripAdapter: SearchHorizontalAdapter
    private lateinit var recentAdapter: SearchHorizontalAdapter
    private lateinit var searchAdapter: SearchHorizontalAdapter
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModelFactory((activity?.application as CocktailsApp).drinkRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val glide = Glide.with(this)
        randomStripAdapter = SearchHorizontalAdapter(glide, this)
        popularStripAdapter = SearchHorizontalAdapter(glide, this)
        recentAdapter = SearchHorizontalAdapter(glide, this)
        loadRandomDrinks()
        loadPopularDrinks()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        activity?.title = getString(R.string.search_title)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupRandomRecyclerView()
        setupPopularRecyclerView()
        setupRecentRecyclerView()

        return binding.root
    }

    private fun setupRandomRecyclerView() {
        val randomRecyclerView = binding.horizontalRandomCocktailsRecycler
        randomRecyclerView.adapter = randomStripAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        randomRecyclerView.layoutManager = layoutManager
    }

    private fun setupPopularRecyclerView() {
        val popularRecyclerView = binding.horizontalPopularRecycler
        popularRecyclerView.adapter = popularStripAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        popularRecyclerView.layoutManager = layoutManager
    }

    private fun setupRecentRecyclerView() {
        val recentRecyclerView = binding.recentRecycler
        recentRecyclerView.adapter = recentAdapter
        recentRecyclerView.layoutManager =
            GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            )
    }

    private fun loadRandomDrinks() {
        viewModel.randomDrinks.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            {
                randomStripAdapter.submitList(it)
            },
            {
                //TODO later
            }
        )
    }

    private fun loadPopularDrinks() {
        viewModel.popularDrinks.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    popularStripAdapter.submitList(it)
                },
                {
                    //TODO later
                }
            )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment()
    }

    override fun openDetails(drink: DrinkItem) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, DrinkDetailsFragment.newInstance(drink))
            .addToBackStack("search_to_details").commit()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}

interface OnItemClick {
    fun openDetails(drink: DrinkItem)
}