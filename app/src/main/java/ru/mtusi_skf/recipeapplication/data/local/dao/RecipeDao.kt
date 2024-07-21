package ru.mtusi_skf.recipeapplication.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.mtusi_skf.recipeapplication.data.local.entity.InstructionEntity
import ru.mtusi_skf.recipeapplication.data.local.entity.RecipeEntity
import ru.mtusi_skf.recipeapplication.data.local.entity.StepEntity

@Dao
interface RecipeDao {

    @Query("SELECT * FROM RecipeEntity")
    fun getAllRecipes(): LiveData<List<RecipeEntity>>

    @Transaction
    @Query("SELECT * FROM RecipeEntity WHERE id = :id")
    fun getRecipeWithInstructions(id: Int): LiveData<RecipeWithInstructions>

    @Transaction
    @Query("SELECT * FROM InstructionEntity WHERE recipeId = :id")
    fun getInstructionsWithSteps(id: Int): LiveData<List<InstructionsWithSteps>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstruction(instruction: InstructionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStep(step: StepEntity)

    @Query("SELECT * FROM InstructionEntity WHERE recipeId = :recipeId")
    suspend fun getInstructionsByRecipeId(recipeId: Int): List<InstructionEntity>

    @Query("DELETE FROM InstructionEntity WHERE id = :instructionId")
    suspend fun deleteInstruction(instructionId: Int)

    @Query("DELETE FROM StepEntity WHERE instructionId = :instructionId")
    suspend fun deleteStepsByInstructionId(instructionId: Int)

    @Query("DELETE FROM RecipeEntity WHERE id = :recipeId")
    suspend fun deleteRecipeById(recipeId: Int)

}

data class RecipeWithInstructions(
    @Embedded val recipe: RecipeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val instructions: List<InstructionEntity>
)

data class InstructionsWithSteps(
    @Embedded val instructions: InstructionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "instructionId"
    )
    val steps: List<StepEntity>
)