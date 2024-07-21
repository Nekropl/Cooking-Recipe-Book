package ru.mtusi_skf.recipeapplication.model

data class InstructionResponse(
    val name: String,
    val steps: List<Step>,
)

data class Step(
    val equipment: List<Equipment>,
    val ingredients: List<Ingredient>,
    val number: Long,
    val step: String
)

data class Equipment(
    val id: Long,
    val image: String,
    val name: String
)

data class Ingredient(
    val id: Long,
    val image: String,
    val name: String,
)