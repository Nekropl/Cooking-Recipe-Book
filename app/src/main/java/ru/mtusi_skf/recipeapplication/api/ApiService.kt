package ru.mtusi_skf.recipeapplication.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mtusi_skf.recipeapplication.model.InstructionResponse
import ru.mtusi_skf.recipeapplication.model.Recipe
import ru.mtusi_skf.recipeapplication.model.RecipeResponse

interface ApiService {
    @GET("recipes/findByIngredients")
    suspend fun getRecipesByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
    ): List<Recipe>

    @GET("recipes/complexSearch")
    suspend fun getRandomRecipesByIngredients(
        @Query("includeIngredients") ingredients: String,
        @Query("number") number: Int,
        @Query("sort") sort: String,
        @Query("apiKey") apiKey: String
    ): Response<RecipeResponse>

    @GET("recipes/complexSearch")
    suspend fun getRecipesByQuery(
        @Query("query") query: String,
        @Query("number") number: Int,
        @Query("sort") sort: String,
        @Query("apiKey") apiKey: String
    ): Response<RecipeResponse>

    @GET("recipes/{id}/analyzedInstructions")
    suspend fun getRecipeInstructionsByID(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): List<InstructionResponse>
}