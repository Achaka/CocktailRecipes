package com.achaka.cocktailrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.forEach
import com.achaka.cocktailrecipes.ui.addrecipe.AddRecipeFragment
import com.achaka.cocktailrecipes.databinding.ActivityMainBinding
import com.achaka.cocktailrecipes.ui.favourites.FavouritesFragment
import com.achaka.cocktailrecipes.ui.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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
        val toggle = ActionBarDrawerToggle(
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
                "SEARCH_FRAGMENT_START"
            ).commit()

        //BottomNavigationView
        binding.bottomNavigationView.selectedItemId = R.id.search_item
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.search_item -> {
                    binding.bottomNavigationView.menu.forEach { menuItem ->
                        menuItem.isEnabled = true
                    }
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
                    binding.bottomNavigationView.menu.forEach { menuItem ->
                        menuItem.isEnabled = true
                    }
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_fragment_container,
                        FavouritesFragment.newInstance(),
                        "FAVOURITES_FRAGMENT"
                    ).addToBackStack("favourites_fragment")
                        .commit()
                    it.isEnabled = false
                    return@setOnItemSelectedListener true
                }
                R.id.shopping_list_item -> {
                    binding.bottomNavigationView.menu.forEach { menuItem ->
                        menuItem.isEnabled = true
                    }
                    it.isEnabled = false
                    return@setOnItemSelectedListener true
                }
                R.id.add_recipe_item -> {
                    binding.bottomNavigationView.menu.forEach { menuItem ->
                        menuItem.isEnabled = true
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.main_fragment_container,
                            AddRecipeFragment.newInstance(),
                            "ADD_RECIPE_FRAGMENT"
                        )
                        .addToBackStack("add_recipe_fragment")
                        .commit()
                    it.isEnabled = false
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
    }
}