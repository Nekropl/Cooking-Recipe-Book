package ru.mtusi_skf.recipeapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.mtusi_skf.recipeapplication.R
import ru.mtusi_skf.recipeapplication.databinding.ItemGridRecipeCardviewBinding
import ru.mtusi_skf.recipeapplication.model.Recipe

class SearchAdapter(
    private val onRecipeClicked: (Recipe) -> Unit
): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var recipes: List<Recipe> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class SearchViewHolder(val binding: ItemGridRecipeCardviewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onRecipeClicked(recipes[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGridRecipeCardviewBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val recipe = recipes[position]
        with(holder.binding) {
            recipeTitle.text = recipe.title
            if (recipe.image.isNotBlank()) {
                Picasso.get().load(recipe.image).into(recipeImg)
            } else {
                recipeImg.setImageResource(R.drawable.ic_image_button_select)
            }
        }
    }
}
