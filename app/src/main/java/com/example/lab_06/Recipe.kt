package com.example.alltherecipes

import android.content.Context
import android.util.Log
import org.json.JSONException
import org.json.JSONObject

class Recipe(
    val title: String,
    val description: String,
    val imageUrl: String,
    val instructionUrl: String,
    val label: String
) {

    companion object {

        private const val TAG = "RecipeLoader"

        fun getRecipesFromFile(filename: String, context: Context): ArrayList<Recipe> {
            val recipeList = ArrayList<Recipe>()
            try {
                val jsonString = loadJsonFromAsset(filename, context)
                if (jsonString == null) {
                    Log.e(TAG, "Failed to load JSON file: $filename")
                    return recipeList
                }

                val json = JSONObject(jsonString)
                val recipes = json.getJSONArray("recipes")

                Log.d(TAG, "Total number of recipes: ${recipes.length()}") // Логирование общего количества рецептов

                for (i in 0 until recipes.length()) {
                    val recipeJson = recipes.getJSONObject(i)
                    try {
                        val recipe = Recipe(
                            title = recipeJson.getString("title"),
                            description = recipeJson.getString("description"),
                            imageUrl = recipeJson.getString("image"),
                            instructionUrl = recipeJson.getString("url"),
                            label = recipeJson.optString("dietLabel", "Unknown") // Safe retrieval with fallback
                        )
                        recipeList.add(recipe)
                        Log.d(TAG, "Loaded recipe: ${recipe.title}") // Логирование каждого загруженного рецепта
                    } catch (e: JSONException) {
                        Log.e(TAG, "Error parsing recipe at index $i", e) // Логирование ошибок для конкретного рецепта
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e(TAG, "Error parsing JSON file: $filename", e)
            }

            return recipeList
        }

        private fun loadJsonFromAsset(filename: String, context: Context): String? {
            return try {
                context.assets.open(filename).use { inputStream ->
                    val size = inputStream.available()
                    val buffer = ByteArray(size)
                    inputStream.read(buffer)
                    String(buffer, Charsets.UTF_8)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e(TAG, "Error loading JSON file: $filename", ex)
                null
            }
        }
    }
}
