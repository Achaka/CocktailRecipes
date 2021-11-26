package com.achaka.cocktailrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.ActionBarDrawerToggle
import com.achaka.cocktailrecipes.addrecipe.AddRecipeFragment
import com.achaka.cocktailrecipes.databinding.ActivityMainBinding

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

        //BottomNavigationView
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.search_item -> {
                    Log.d(TAG, "search_click")
                    supportFragmentManager.beginTransaction()
                }
                R.id.favourites_item -> {
                    Log.d(TAG, "fav_click")
                    supportFragmentManager.beginTransaction()
                }
                R.id.shopping_list_item -> {
                    Log.d(TAG, "shl_click")
                    supportFragmentManager.beginTransaction()
                }
                R.id.add_recipe_item -> {
                    Log.d(TAG, "recipe_click")
                    supportFragmentManager.beginTransaction()
                        .add(R.id.main_fragment_container, AddRecipeFragment.newInstance(), "ADD_RECIPE_FRAGMENT")
                        .addToBackStack("first_invocation")
                        .commit()
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}