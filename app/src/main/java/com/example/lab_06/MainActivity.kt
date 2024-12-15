package com.example.lab_06

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alltherecipes.Recipe

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    companion object {
        private const val RECIPE_JSON_FILE = "recipes.json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.recipe_list_view)
        val recipeList = Recipe.getRecipesFromFile(RECIPE_JSON_FILE, this)
        if (recipeList.isEmpty()) {
            android.util.Log.e("MainActivity", "No recipes found in the JSON file.")
        }
        val listItems = recipeList.map { it.title }.toTypedArray()
        val adapter = RecipeAdapter(this, recipeList)
        listView.adapter = adapter

        val context = this

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedRecipe = recipeList[position]
            val detailIntent = RecipeDetailActivity.newIntent(context, selectedRecipe)
            startActivity(detailIntent)
        }

    }
}
