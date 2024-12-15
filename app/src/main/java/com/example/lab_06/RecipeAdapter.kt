package com.example.lab_06

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.alltherecipes.Recipe
import com.squareup.picasso.Picasso

class RecipeAdapter(
    context: Context,
    private val dataSource: ArrayList<Recipe>
) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_recipe, parent, false)
        val titleTextView = rowView.findViewById<TextView>(R.id.recipe_list_title)
        val subtitleTextView = rowView.findViewById<TextView>(R.id.recipe_list_subtitle)
        val detailTextView = rowView.findViewById<TextView>(R.id.recipe_list_detail)
        val thumbnailImageView = rowView.findViewById<ImageView>(R.id.recipe_list_thumbnail)
        val recipe = getItem(position) as Recipe
        titleTextView.text = recipe.title
        subtitleTextView.text = recipe.description
        detailTextView.text = recipe.label
        Picasso.get()
            .load(recipe.imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .into(thumbnailImageView)

        return rowView
    }
}
