package com.achaka.cocktailrecipes.ui.shoppinglist

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.cocktailrecipes.R
import com.achaka.cocktailrecipes.databinding.FragmentShoppingListBinding
import com.achaka.cocktailrecipes.domain.model.asShoppingListItem
import com.achaka.cocktailrecipes.ui.details.DrinkDetailsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class ShoppingListFragment : Fragment() {

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DrinkDetailsViewModel by activityViewModels()

    private val adapter = ShoppingListRecyclerViewAdapter()
    private val headerAdapter = ShoppingListHeaderAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.shoppingList.onEach { list ->
                adapter.submitList(list.map { item -> item.asShoppingListItem() })
            }.collect()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        activity?.title = getString(R.string.shopping_list_activity_name)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerShoppingList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val concatAdapter = ConcatAdapter(headerAdapter, adapter)
        recyclerView.adapter = concatAdapter

        val divider = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.shopping_list_divider)
            ?.let { divider.setDrawable(it) }
        recyclerView.addItemDecoration(divider)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShoppingListFragment()
    }
}