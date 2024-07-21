package ru.mtusi_skf.recipeapplication.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.mtusi_skf.recipeapplication.R
import ru.mtusi_skf.recipeapplication.databinding.ItemHomeRecipeCardviewBinding
import ru.mtusi_skf.recipeapplication.model.Recipe

class HomeAdapter(
    private val onRecipeClicked: (Recipe) -> Unit
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var recipes: List<Recipe> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class HomeViewHolder(
        val binding: ItemHomeRecipeCardviewBinding,
        onRecipeClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onRecipeClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeRecipeCardviewBinding.inflate(inflater, parent, false)
        return HomeViewHolder(binding) { position ->
            onRecipeClicked(recipes[position])
        }
    }

    override fun getItemCount(): Int {
        return recipes.count()
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
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
