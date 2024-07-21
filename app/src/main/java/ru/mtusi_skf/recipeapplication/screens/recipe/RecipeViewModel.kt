package ru.mtusi_skf.recipeapplication.screens.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mtusi_skf.recipeapplication.api.RetrofitRepository
import ru.mtusi_skf.recipeapplication.model.InstructionResponse

class RecipeViewModel : ViewModel() {

    private val repository = RetrofitRepository()
    private val _instructionsLD = MutableLiveData<List<InstructionResponse>>()
    val instructionsLD: LiveData<List<InstructionResponse>> = _instructionsLD

    fun fetchInstructions(id : Int) {
        viewModelScope.launch {
            try {
                val instructions = repository.getRecipeInstructionsByID(id)
                _instructionsLD.postValue(instructions)
            } catch (e: Exception) {
                e.printStackTrace() // Обработка ошибок
            }
        }
    }

}