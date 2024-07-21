package ru.mtusi_skf.recipeapplication.screens.dbrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mtusi_skf.recipeapplication.data.repository.RecipeRepository
class DBRecipeViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DBRecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DBRecipeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}