package com.achaka.cocktailrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.ActionBarDrawerToggle
import com.achaka.cocktailrecipes.ui.addrecipe.AddRecipeFragment
import com.achaka.cocktailrecipes.databinding.ActivityMainBinding
import com.achaka.cocktailrecipes.ui.favourites.FavouritesFragment
import com.achaka.cocktailrecipes.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.simpleName

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //SplashScreen
        setTheme(R.style.Theme_CocktailRecipes)
        val view = binding.root
        setContentView(view)

        //Toolbar
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //Drawer
        val drawerLayout = binding.drawerLayout
        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.openDrawerContentDescRes,
            R.string.closeDrawerContentDescRes
        )
        toggle.syncState()

        //inflate StartFragment
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                SearchFragment.newInstance(),
                "SEARCH_FRAGMENT"
            ).addToBackStack("search_fragment")
            .commit()

        //BottomNavigationView
        binding.bottomNavigationView.selectedItemId = R.id.search_item
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.search_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.main_fragment_container,
                            SearchFragment.newInstance(),
                            "SEARCH_FRAGMENT"
                        ).addToBackStack("search_fragment")
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.favourites_item -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_fragment_container,
                        FavouritesFragment.newInstance(),
                        "FAVOURITES_FRAGMENT"
                    ).addToBackStack("favourites_fragment")
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.shopping_list_item -> {
//                    supportFragmentManager.beginTransaction().replace(
//
//                    ).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.add_recipe_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.main_fragment_container,
                            AddRecipeFragment.newInstance(),
                            "ADD_RECIPE_FRAGMENT"
                        )
                        .addToBackStack("add_recipe_fragment")
                        .commit()
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
    }
}