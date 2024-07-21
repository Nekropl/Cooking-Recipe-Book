package ru.mtusi_skf.recipeapplication.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mtusi_skf.recipeapplication.api.RetrofitRepository

class HomeViewModelFactory(private val repository: RetrofitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}