package ru.mtusi_skf.recipeapplication.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mtusi_skf.recipeapplication.R
import ru.mtusi_skf.recipeapplication.data.local.entity.RecipeEntity
import ru.mtusi_skf.recipeapplication.databinding.ItemSavedRecipeBinding

class SavedAdapter(
    private val onRecipeClicked: (RecipeEntity) -> Unit,
    private val onDeleteClick: (RecipeEntity) -> Unit
) : RecyclerView.Adapter<SavedAdapter.SavedViewHolder>() {

    var recipes: List<RecipeEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class SavedViewHolder(
        val binding: ItemSavedRecipeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onRecipeClicked(recipes[position])
                }
            }

            binding.deleteButton.setOnClickListener {
                onDeleteClick(recipes[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSavedRecipeBinding.inflate(inflater, parent, false)
        return SavedViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        val recipe = recipes[position]
        with(holder.binding) {
            recipeTitle.text = recipe.title
            if (!recipe.imageUri.isNullOrEmpty()) {
                val uri = Uri.parse(recipe.imageUri)
                recipeImg.setImageURI(uri)
            } else {
                recipeImg.setImageResource(R.drawable.ic_image_button_select)
            }
        }
    }
}