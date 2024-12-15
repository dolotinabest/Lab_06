package com.example.lab_06

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.alltherecipes.Recipe
import com.squareup.picasso.Picasso

class RecipeAdapter(
    context: Context,
    private val dataSource: ArrayList<Recipe>
) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val titleTypeFace = ResourcesCompat.getFont(context, R.font.josefinsans)
    private val subtitleTypeFace = ResourcesCompat.getFont(context, R.font.josefinsans_semibolditalic)
    private val detailTypeFace = ResourcesCompat.getFont(context, R.font.quicksand_bold)

    companion object {
        private val LABEL_COLORS = hashMapOf(
            "Low-Carb" to R.color.colorLowCarb,
            "Low-Fat" to R.color.colorLowFat,
            "Low-Sodium" to R.color.colorLowSodium,
            "Medium-Carb" to R.color.colorMediumCarb,
            "Vegetarian" to R.color.colorVegetarian,
            "Balanced" to R.color.colorBalanced
        )
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val view: View

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_recipe, parent, false)
            holder = ViewHolder()
            holder.titleView = view.findViewById(R.id.recipe_list_title)
            holder.subtitleView = view.findViewById(R.id.recipe_list_subtitle)
            holder.detailView = view.findViewById(R.id.recipe_list_detail)
            holder.thumbnailImageView = view.findViewById(R.id.recipe_list_thumbnail)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val recipe = getItem(position) as Recipe

        holder.titleView.text = recipe.title
        holder.subtitleView.text = recipe.description
        holder.detailView.text = recipe.label
        Picasso.get()
            .load(recipe.imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.thumbnailImageView)

        holder.titleView.typeface = titleTypeFace
        holder.subtitleView.typeface = subtitleTypeFace
        holder.detailView.typeface = detailTypeFace
        holder.detailView.setTextColor(
            ResourcesCompat.getColor(
                view.resources,
                LABEL_COLORS[recipe.label] ?: R.color.colorPrimary,
                null
            )
        )

        return view
    }

    private class ViewHolder {
        lateinit var titleView: TextView
        lateinit var subtitleView: TextView
        lateinit var detailView: TextView
        lateinit var thumbnailImageView: ImageView
    }
}
