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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddRecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddRecipeFragment : Fragment() {

    private var _binding: FragmentAddRecipeBinding? = null
    private val binding get() = _binding!!
    private val adapter = AddRecipeAdapter()
    private val viewModel: AddRecipeViewModel by viewModels {
        AddRecipeViewModelFactory((activity?.application as CocktailsApp).addRecipeRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.title = getString(R.string.add_recipe_title)
        _binding = FragmentAddRecipeBinding.inflate(inflater, container, false)


        val recyclerView = binding.addRecipeRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.submitList(mutableListOf(IngredientMeasureItem("",null, null, null, Units.NONE)))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addMeasureFab.setOnClickListener {
            val currentList = adapter.currentList
            val newList = mutableListOf<IngredientMeasureItem>()
            newList.addAll(currentList)
            newList.add(IngredientMeasureItem("",null, null, null, Units.NONE))
            adapter.submitList(newList)
        }

        binding.photo.setOnClickListener {
            // take photo
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    //MENU
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddRecipeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            AddRecipeFragment().apply {
                arguments = Bundle().apply {
//
                }
            }
    }
}