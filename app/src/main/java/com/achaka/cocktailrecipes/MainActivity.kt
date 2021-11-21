package com.achaka.cocktailrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
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
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}