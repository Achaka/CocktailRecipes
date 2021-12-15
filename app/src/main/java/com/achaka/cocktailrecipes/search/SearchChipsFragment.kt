package com.achaka.cocktailrecipes.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.achaka.cocktailrecipes.CocktailsApp
import com.achaka.cocktailrecipes.databinding.FragmentSearchChipsBinding

class SearchChipsFragment : Fragment() {

    private var _binding: FragmentSearchChipsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory(
            (activity?.application as CocktailsApp).drinkRepository,
            (activity?.application as CocktailsApp).searchRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchChipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.byDrinkName.setOnCheckedChangeListener { _, checked ->
            if (checked)
                viewModel.setQueryParams(QueryParams(SearchType.DRINK_BY_DRINK_NAME))
        }
        binding.drinkByIngredientName.setOnCheckedChangeListener { _, checked ->
            if (checked)
                viewModel.setQueryParams(QueryParams(SearchType.DRINK_BY_INGREDIENT_NAME))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchChipsFragment()
    }
}
