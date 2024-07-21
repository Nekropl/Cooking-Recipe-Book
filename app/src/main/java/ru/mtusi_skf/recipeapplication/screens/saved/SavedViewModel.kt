package ru.mtusi_skf.recipeapplication.screens.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mtusi_skf.recipeapplication.data.local.entity.RecipeEntity
import ru.mtusi_skf.recipeapplication.data.repository.RecipeRepository

class SavedViewModel(private val repository: RecipeRepository) : ViewModel() {
    val recipesLD: LiveData<List<RecipeEntity>> = repository.getAllRecipes()

    fun deleteRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.deleteRecipeWithInstructionsAndSteps(recipe.id)
        }
    }
}