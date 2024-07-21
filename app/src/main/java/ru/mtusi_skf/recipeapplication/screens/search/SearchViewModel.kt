package ru.mtusi_skf.recipeapplication.screens.search

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mtusi_skf.recipeapplication.api.RetrofitRepository
import ru.mtusi_skf.recipeapplication.model.Recipe

class SearchViewModel(private val repository: RetrofitRepository) : ViewModel() {

    private val _recipesLD = MutableLiveData<List<Recipe>>()
    val recipesLD: LiveData<List<Recipe>> = _recipesLD
    private val _emptyScreenTextVisibility = MutableLiveData(View.VISIBLE)
    val emptyScreenTextVisibility: LiveData<Int> = _emptyScreenTextVisibility
    var query: String = ""
    fun setEmptyScreenTextVisibility(visibility: Int) {
        _emptyScreenTextVisibility.value = visibility
    }
    fun fetchRecipes() {
        viewModelScope.launch {
            try {
                // Выполняем API запрос
                val response = repository.getRecipesByQuery(query)

                if (response.isSuccessful) {
                    val recipes = response.body()?.results ?: emptyList()
                    _recipesLD.postValue(recipes)
                } else {
                    // Обработка ошибки
                    Log.e("SearchViewModel", "Error fetching recipes: ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace() // Обработка ошибок
            }
        }
    }
}