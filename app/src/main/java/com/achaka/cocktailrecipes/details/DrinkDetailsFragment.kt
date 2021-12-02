package com.achaka.cocktailrecipes.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.achaka.cocktailrecipes.databinding.FragmentDrinkDetailsBinding
import com.achaka.cocktailrecipes.model.domain.Drink
import com.bumptech.glide.Glide

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val DRINK_ARG = "drink"

class DrinkDetailsFragment : Fragment() {

    private var drink: Drink? = null

    private var _binding: FragmentDrinkDetailsBinding? = null
    private val binding get() = _binding!!
    private val adapter = IngredientMeasuresRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            drink = it.getParcelable(DRINK_ARG)
        }
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
        binding.name.text = drink?.name
        binding.instructions.text = drink?.instructions
        Glide.with(this).load(drink?.thumbUrl).into(binding.image)
        val recyclerView = binding.ingredientsRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.submitList(drink?.ingredientMeasureItems)
        recyclerView.adapter = adapter

        Log.d("drink INGREDIENTS", drink!!.ingredientMeasureItems.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance(drink: Drink) =
            DrinkDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DRINK_ARG, drink)
                }
            }
    }
}