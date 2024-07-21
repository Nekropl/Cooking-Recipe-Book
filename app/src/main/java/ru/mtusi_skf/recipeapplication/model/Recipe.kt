package ru.mtusi_skf.recipeapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id : Int,
    val title: String,
    val image: String
) : Parcelable

@Parcelize
data class RecipeResponse(
    val results: List<Recipe>
) : Parcelable