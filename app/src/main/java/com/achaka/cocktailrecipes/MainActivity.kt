package com.achaka.cocktailrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import by.kirich1409.viewbindingdelegate.viewBinding
import com.achaka.cocktailrecipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CocktailRecipes)

        val view = binding.root
        setContentView(view)
    }
}