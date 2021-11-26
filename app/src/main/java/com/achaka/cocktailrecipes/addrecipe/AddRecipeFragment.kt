package com.achaka.cocktailrecipes.addrecipe

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.cocktailrecipes.R
import com.achaka.cocktailrecipes.databinding.FragmentAddRecipeBinding

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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentAddRecipeBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


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

        binding.addRecipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.addRecipeRecyclerView.adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                //write to db
                Toast.makeText(requireContext(),binding.name.text.toString()+binding.instructions.text.toString(), Toast.LENGTH_SHORT ).show()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addIngredientLayout() {

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