package com.achaka.cocktailrecipes.addrecipe

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.cocktailrecipes.CocktailsApp
import com.achaka.cocktailrecipes.R
import com.achaka.cocktailrecipes.databinding.FragmentAddRecipeBinding
import com.achaka.cocktailrecipes.model.domain.*

class AddRecipeFragment : Fragment() {

    private var _binding: FragmentAddRecipeBinding? = null
    private val binding get() = _binding!!
    private val adapter = AddRecipeAdapter()
    private val viewModel: AddRecipeViewModel by viewModels {
        AddRecipeViewModelFactory((activity?.application as CocktailsApp).userDrinkRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        adapter.submitList(
            mutableListOf(getDefaultIngredientItem())
        )
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.title = getString(R.string.add_recipe_title)
        _binding = FragmentAddRecipeBinding.inflate(inflater, container, false)

        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addMeasureFab.setOnClickListener {
            addAdapterItem()
        }

        binding.photo.setOnClickListener {
            // take photo
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    private fun addAdapterItem() {
        val currentList = adapter.currentList
        val newList = mutableListOf<IngredientMeasureItem>()
        newList.addAll(currentList)
        newList.add(getDefaultIngredientItem())
        adapter.submitList(newList)
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.addRecipeRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getDefaultIngredientItem(): IngredientMeasureItem {
        return IngredientMeasureItem(
            "",
            null,
            null,
            null,
            Pair(Units.NONE, "")
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_recipe_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_recipe_confirm -> {
                viewModel.insertUserDrink(
                    UserDrink(
                        id = 0,
                        name = binding.nameHeader.text.toString(),
                        tags = null,
                        videoUrl = "",
                        category = Category.OTHER,
                        IBA = "",
                        alcoholic = Alcoholic.NON_ALCOHOLIC,
                        glassType = GlassType.HIGHBALL,
                        instructions = binding.instructions.text.toString(),
                        ingredientMeasureItems = adapter.currentList,
                        imageUri = "",
                        isUserDrink = true
                    )
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddRecipeFragment()
    }
}