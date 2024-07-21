package ru.mtusi_skf.recipeapplication.api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRepository {

    val apiKey = API_KEY.API_KEY

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(ApiService::class.java)

    suspend fun getRecipesByQuery(
        query: String,
        number: Int = 60,
        sort: String = "random"
    ) = api.getRecipesByQuery(query, number, sort, apiKey)

    suspend fun getRecipesByIngredients(
        ingredients: String,
        number: Int = 30,
    ) = api.getRecipesByIngredients(ingredients, number, apiKey)

    suspend fun getRandomRecipesByIngredients(
        ingredients: String,
        number: Int = 30,
        sort: String = "random"
    ) = api.getRandomRecipesByIngredients(ingredients, number, sort, apiKey)

    suspend fun getRecipeInstructionsByID(id: Int) = api.getRecipeInstructionsByID(
        id,
        apiKey
    )

}