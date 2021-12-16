package com.achaka.cocktailrecipes.ui.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.cocktailrecipes.MainRecyclerViewAdapter
import com.achaka.cocktailrecipes.R
import com.achaka.cocktailrecipes.State
import com.achaka.cocktailrecipes.databinding.FragmentSearchBinding
import com.achaka.cocktailrecipes.ui.details.DrinkDetailsFragment
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment : Fragment(), OnItemClick {

    private var queryParams = QueryParams(SearchType.DRINK_BY_DRINK_NAME)
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var randomStripAdapter: SearchHorizontalAdapter
    private lateinit var popularStripAdapter: SearchHorizontalAdapter
    private lateinit var recentAdapter: SearchHorizontalAdapter
    private lateinit var searchAdapter: MainRecyclerViewAdapter

    val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val glide = Glide.with(this)
        randomStripAdapter = SearchHorizontalAdapter(glide, this)
        popularStripAdapter = SearchHorizontalAdapter(glide, this)
        recentAdapter = SearchHorizontalAdapter(glide, this)
        searchAdapter = MainRecyclerViewAdapter(glide, this)
        loadRandomDrinks()
        loadPopularDrinks()
        loadRecentDrinks()
        getQueryParams()
    }

    private fun getQueryParams() {
        lifecycleScope.launchWhenStarted {
            viewModel.queryParams.onEach {
                queryParams = it
            }.collect()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.title = getString(R.string.search_title)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }


//    private fun setupRecentRecyclerView() {
//        val recentRecyclerView = binding.recentRecycler
//        recentRecyclerView.adapter = recentAdapter
//        recentRecyclerView.layoutManager =
//            GridLayoutManager(
//                requireContext(),
//                2,
//                GridLayoutManager.VERTICAL,
//                false
//            )
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRandomRecyclerView()
        setupPopularRecyclerView()
//        setupRecentRecyclerView()
        setupSearchRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }


    private fun setupSearchRecyclerView() {
        if (searchAdapter.currentList.isEmpty()) {
            binding.searchResultsHeader.visibility = View.GONE
        }
        val searchRecyclerView = binding.searchCocktailsRecyclerView
        searchRecyclerView.layoutManager =
            GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            )
        searchRecyclerView.adapter = searchAdapter
        searchRecyclerView.isNestedScrollingEnabled = false
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

    override fun openDetails(drink: DrinkItem) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, DrinkDetailsFragment.newInstance(drink))
            .addToBackStack("search_to_details").commit()
    }

    private fun loadRandomDrinks() {
        viewModel.randomDrinksSubject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    randomStripAdapter.submitList(it)
                },
                {
                    Toast.makeText(requireContext(), "Unknown Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
            )
    }

    private fun loadPopularDrinks() {
        viewModel.popularDrinksSubject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    popularStripAdapter.submitList(it)
                },
                {
                    Toast.makeText(requireContext(), "Unknown Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
            )

    }

    private fun loadRecentDrinks() {
        viewModel.recentDrinksSubject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    recentAdapter.submitList(it)
                },
                {

                }
            )
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchView = menu.findItem(R.id.request_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    getQueryParams()
                    viewModel.searchDrink(query, queryParams)
                    lifecycleScope.launchWhenStarted {
                        viewModel.state.onEach { state ->
                            when (state) {
                                is State.Loading -> {

                                }
                                is State.Error -> {
                                    Toast.makeText(
                                        requireContext(),
                                        state.exceptionMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                is State.Success -> {
                                    binding.searchResultsHeader.visibility = View.VISIBLE
                                    binding.searchCocktailsRecyclerView.visibility = View.VISIBLE
                                    searchAdapter.submitList(state.data)
                                    childFragmentManager.beginTransaction()
                                        .hide(SearchChipsFragment())
                                        .commit()
                                }
                                else -> {
                                    //do nothing
                                }
                            }
                        }.collect()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                childFragmentManager.beginTransaction()
                    .replace(R.id.search_fragment_container, SearchChipsFragment.newInstance())
                    .commit()
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return true
    }

}

interface OnItemClick {
    fun openDetails(drink: DrinkItem)
}