package ru.mtusi_skf.recipeapplication.screens.recipe

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import ru.mtusi_skf.recipeapplication.R
import ru.mtusi_skf.recipeapplication.adapters.RecipeAdapter
import ru.mtusi_skf.recipeapplication.databinding.FragmentRecipeBinding
import ru.mtusi_skf.recipeapplication.screens.base.CustomBaseFragment

class RecipeFragment : CustomBaseFragment(R.layout.fragment_recipe) {
    override var bottomNavigationViewVisibility = View.GONE
    private val args: RecipeFragmentArgs by navArgs()
    private lateinit var binding: FragmentRecipeBinding
    private lateinit var adapter: RecipeAdapter
    private lateinit var viewModel: RecipeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipe = args.recipe
        binding = FragmentRecipeBinding.bind(view)
        with(binding) {
            recipeTitle.text = recipe.title
            if (recipe.image.isNotBlank()) {
                Picasso.get().load(recipe.image).into(recipeImage)
            } else {
                recipeImage.setImageResource(R.drawable.ic_image_button_select)
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        adapter = RecipeAdapter()

        viewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        val layoutManager = LinearLayoutManager(context)
        binding.instructionsRecycler.layoutManager = layoutManager
        binding.instructionsRecycler.adapter = adapter

        viewModel.instructionsLD.observe(viewLifecycleOwner) { instructions ->
            adapter.instructions = instructions
        }

        viewModel.fetchInstructions(recipe.id)
    }
}