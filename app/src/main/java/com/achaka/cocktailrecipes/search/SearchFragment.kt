package com.achaka.cocktailrecipes.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.cocktailrecipes.CocktailsApp
import com.achaka.cocktailrecipes.R
import com.achaka.cocktailrecipes.databinding.FragmentSearchBinding
import com.achaka.cocktailrecipes.details.DrinkDetailsFragment
import com.achaka.cocktailrecipes.model.domain.Drink
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

        val recentRecyclerView = binding.horizontalRandomCocktailsRecycler

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

    private fun loadRecentRecyclerView() {

    }

    private fun loadRandomDrinks() {
        viewModel.getRandomDrinks().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                randomStripAdapter.submitList(it)
            }, {
                //TODO
            })
    }

    private fun loadPopularDrinks() {
        viewModel.getPopularDrinks().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                popularStripAdapter.submitList(it)
            }, {
                //TODO
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment()
    }

    override fun openDetails(drink: Drink) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, DrinkDetailsFragment.newInstance(drink))
            .addToBackStack("search_to_details").commit()
    }
}

interface OnItemClick {
    fun openDetails(drink: Drink)
}