package com.example.lab_06

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alltherecipes.Recipe

class RecipeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val title = intent.extras?.getString("title")
        val url = intent.extras?.getString("url")
        setTitle(title)
        val webView = findViewById<WebView>(R.id.detail_web_view).apply {
            webViewClient = WebViewClient()
            loadUrl(url.toString())
        }
    }
    private lateinit var webView: WebView
    companion object{
        const val EXTRA_TITLE = "title"
        const val EXTRA_URL = "url"
        fun newIntent(context: Context , recipe: Recipe): Intent{
            val detailIntent = Intent(context,RecipeDetailActivity::class.java)

            detailIntent.putExtra(EXTRA_TITLE,recipe.title)
            detailIntent.putExtra(EXTRA_URL,recipe.instructionUrl)

            return detailIntent
        }

    }
}