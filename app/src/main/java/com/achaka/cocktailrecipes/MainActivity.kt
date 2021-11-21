package com.achaka.cocktailrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.achaka.cocktailrecipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CocktailRecipes)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.search_item -> {
                    supportFragmentManager.beginTransaction()
                }
                R.id.favourites_item -> {
                    supportFragmentManager.beginTransaction()
                }
                R.id.shopping_list_item -> {
                    supportFragmentManager.beginTransaction()
                }
                R.id.add_recipe_item -> {
                    supportFragmentManager.beginTransaction()
                }
            }
            return@setOnItemSelectedListener true
        }

        val view = binding.root
        setContentView(view)
    }
}