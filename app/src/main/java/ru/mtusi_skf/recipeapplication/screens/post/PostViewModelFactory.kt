package ru.mtusi_skf.recipeapplication.screens.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mtusi_skf.recipeapplication.data.repository.RecipeRepository

class PostViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}