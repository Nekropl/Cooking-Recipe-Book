package ru.mtusi_skf.recipeapplication.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mtusi_skf.recipeapplication.api.RetrofitRepository

class SearchViewModelFactory(private val repository: RetrofitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}