package ru.mtusi_skf.recipeapplication.screens.home

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mtusi_skf.recipeapplication.api.RetrofitRepository
import ru.mtusi_skf.recipeapplication.model.ChipIngredient
import ru.mtusi_skf.recipeapplication.model.Recipe

class HomeViewModel(private val repository: RetrofitRepository) : ViewModel(){
    private val _ingredientsLD = MutableLiveData<List<ChipIngredient>>()
    val ingredientsLD: LiveData<List<ChipIngredient>> get() = _ingredientsLD

    private val _recipesLD = MutableLiveData<List<Recipe>>()
    val recipesLD: LiveData<List<Recipe>> = _recipesLD

    private val selectedIngredientsList = mutableListOf<String>()
    val selectedIngredients: List<String> get() = selectedIngredientsList

    private val _emptyScreenTextVisibility = MutableLiveData(View.VISIBLE)
    val emptyScreenTextVisibility: LiveData<Int> = _emptyScreenTextVisibility

    init {
        _ingredientsLD.value = listOf(
            ChipIngredient("Курица", "chicken"),
            ChipIngredient("Говядина", "beef"),
            ChipIngredient("Свинина", "pork"),
            ChipIngredient("Рыба", "fish"),
            ChipIngredient("Помидор", "tomato"),
            ChipIngredient("Огурец", "cucumber"),
            ChipIngredient("Морковь", "carrot"),
            ChipIngredient("Капуста", "cabbage"),
            ChipIngredient("Яйцо", "egg")

        ) // Инициализируем список чипов
    }

    fun setEmptyScreenTextVisibility(visibility: Int) {
        _emptyScreenTextVisibility.value = visibility
    }

    fun fetchRecipes() {
        viewModelScope.launch {
            try {
                // Выполняем API запрос
                val recipes = repository.getRecipesByIngredients(
                    selectedIngredientsList.joinToString(separator = ","))
                _recipesLD.postValue(recipes)
            } catch (e: Exception) {
                e.printStackTrace() // Обработка ошибок
            }
        }
    }

    fun fetchRandomRecipes() {
        viewModelScope.launch {
            try {
                // Выполняем API запрос
                val response = repository.getRandomRecipesByIngredients(
                    selectedIngredientsList.joinToString(separator = ",")
                )

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

    fun addToSelectedIngredients(chipIngredient: ChipIngredient) {
        if (!selectedIngredientsList.contains(chipIngredient.apiName)) {
            selectedIngredientsList.add(chipIngredient.apiName)
        }
    }

    fun removeFromSelectedIngredients(chipIngredient: ChipIngredient) {
        selectedIngredientsList.remove(chipIngredient.apiName)
    }
}