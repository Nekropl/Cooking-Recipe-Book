package ru.mtusi_skf.recipeapplication.screens.post

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.mtusi_skf.recipeapplication.data.repository.RecipeRepository
import kotlinx.coroutines.launch
import ru.mtusi_skf.recipeapplication.data.local.entity.InstructionEntity
import ru.mtusi_skf.recipeapplication.data.local.entity.RecipeEntity
import ru.mtusi_skf.recipeapplication.data.local.entity.StepEntity
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.io.FileOutputStream

class PostViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _imageBitmap = MutableLiveData<Bitmap>()
    val imageBitmap: LiveData<Bitmap> get() = _imageBitmap

    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri> get() = _imageUri

    fun processSelectedImage(bitmap: Bitmap, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val uri = saveImageToFile(bitmap, context)
            _imageUri.postValue(uri)
            _imageBitmap.postValue(bitmap)
        }
    }

    private fun saveImageToFile(bitmap: Bitmap, context: Context): Uri {
        val file = File(context.filesDir, "recipe_image_${System.currentTimeMillis()}.png")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        return Uri.fromFile(file)
    }

    fun insertRecipeWithInstructionsAndSteps(
        recipe: RecipeEntity,
        instruction: InstructionEntity,
        steps: List<StepEntity>
    ) = viewModelScope.launch {
        repository.insertRecipeWithInstructionsAndSteps(recipe, instruction, steps)
    }
}
