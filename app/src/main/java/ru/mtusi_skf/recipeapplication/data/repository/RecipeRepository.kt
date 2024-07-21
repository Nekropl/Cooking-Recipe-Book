package ru.mtusi_skf.recipeapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.room.Transaction
import ru.mtusi_skf.recipeapplication.data.local.dao.RecipeDao
import ru.mtusi_skf.recipeapplication.data.local.entity.InstructionEntity
import ru.mtusi_skf.recipeapplication.data.local.entity.RecipeEntity
import ru.mtusi_skf.recipeapplication.data.local.entity.StepEntity
import ru.mtusi_skf.recipeapplication.data.local.dao.InstructionsWithSteps
import ru.mtusi_skf.recipeapplication.data.local.dao.RecipeWithInstructions

class RecipeRepository(private val recipeDao: RecipeDao) {

    fun getAllRecipes(): LiveData<List<RecipeEntity>> {
        return recipeDao.getAllRecipes()
    }

    fun getRecipeWithInstructions(id: Int): LiveData<RecipeWithInstructions> {
        return recipeDao.getRecipeWithInstructions(id)
    }

    fun getInstructionsWithSteps(id: Int): LiveData<List<InstructionsWithSteps>> {
        return recipeDao.getInstructionsWithSteps(id)
    }

    suspend fun insertRecipe(recipe: RecipeEntity): Long {
        return recipeDao.insertRecipe(recipe)
    }

    suspend fun insertInstruction(instruction: InstructionEntity): Long {
        return recipeDao.insertInstruction(instruction)
    }

    suspend fun insertStep(step: StepEntity) {
        recipeDao.insertStep(step)
    }

    suspend fun deleteRecipeWithInstructionsAndSteps(recipeId: Int) {
        val instructions = recipeDao.getInstructionsByRecipeId(recipeId)
        instructions.forEach { instruction ->
            recipeDao.deleteStepsByInstructionId(instruction.id)
            recipeDao.deleteInstruction(instruction.id)
        }
        recipeDao.deleteRecipeById(recipeId)
    }

    @Transaction
    suspend fun insertRecipeWithInstructionsAndSteps(recipe: RecipeEntity, instruction: InstructionEntity, steps: List<StepEntity>) {
        val recipeId = insertRecipe(recipe).toInt()
        instruction.recipeId = recipeId
        val instructionId = insertInstruction(instruction).toInt()
        steps.forEach { step ->
            step.instructionId = instructionId
            insertStep(step)
        }
    }
}