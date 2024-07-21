package ru.mtusi_skf.recipeapplication.screens.dbrecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mtusi_skf.recipeapplication.data.local.dao.InstructionsWithSteps
import ru.mtusi_skf.recipeapplication.data.repository.RecipeRepository

class DBRecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val _instructionsLD = MutableLiveData<List<InstructionsWithSteps>>()
    var instructionsLD: LiveData<List<InstructionsWithSteps>> = _instructionsLD

    fun fetchRecipes(id : Int) {
        instructionsLD = repository.getInstructionsWithSteps(id)
    }
}