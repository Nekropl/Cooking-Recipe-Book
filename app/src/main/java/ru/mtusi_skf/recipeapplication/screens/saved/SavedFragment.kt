package ru.mtusi_skf.recipeapplication.screens.saved

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ru.mtusi_skf.recipeapplication.R
import ru.mtusi_skf.recipeapplication.adapters.SavedAdapter
import ru.mtusi_skf.recipeapplication.data.local.db.AppDatabase
import ru.mtusi_skf.recipeapplication.data.repository.RecipeRepository
import ru.mtusi_skf.recipeapplication.databinding.FragmentSavedBinding
import ru.mtusi_skf.recipeapplication.screens.base.CustomBaseFragment

class SavedFragment : CustomBaseFragment(R.layout.fragment_saved) {
    override var bottomNavigationViewVisibility = View.VISIBLE

    private lateinit var binding: FragmentSavedBinding
    private lateinit var adapter: SavedAdapter
    private lateinit var viewModel: SavedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        adapter = SavedAdapter(
            onRecipeClicked = {  selectedRecipe ->
            val action = SavedFragmentDirections.actionSavedFragmentToDBRecipeFragment(selectedRecipe)
            findNavController().navigate(action)
        },
            onDeleteClick = { selectedRecipe ->
                viewModel.deleteRecipe(selectedRecipe)
            }
        )

        // Инициализация базы данных и репозитория
        val db = AppDatabase.getDatabase(requireContext())
        val repository = RecipeRepository(db.recipeDao())

        viewModel = ViewModelProvider(this, SavedViewModelFactory(repository))[SavedViewModel::class.java]
        val layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        viewModel.recipesLD.observe(viewLifecycleOwner) { recipes ->
            adapter.recipes = recipes
        }
    }
}