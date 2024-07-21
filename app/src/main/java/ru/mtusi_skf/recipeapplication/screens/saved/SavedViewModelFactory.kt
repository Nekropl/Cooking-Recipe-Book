package ru.mtusi_skf.recipeapplication.screens.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mtusi_skf.recipeapplication.data.repository.RecipeRepository

class SavedViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}