package ru.mtusi_skf.recipeapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.mtusi_skf.recipeapplication.R
import ru.mtusi_skf.recipeapplication.databinding.ItemInstructionStepsItemsBinding
import ru.mtusi_skf.recipeapplication.model.Ingredient

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    var ingredients: List<Ingredient> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class IngredientsViewHolder(
        val binding: ItemInstructionStepsItemsBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInstructionStepsItemsBinding.inflate(inflater, parent, false)
        return IngredientsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = ingredients[position]
        with(holder.binding) {
            titleInstructionStepsItems.text = ingredient.name
            if (ingredient.image.isNotBlank()) {
                Picasso.get().load(ingredient.image).into(imageInstructionStepsItems)
            } else {
                imageInstructionStepsItems.setImageResource(R.drawable.ic_image_button_select)
            }
        }
    }
}