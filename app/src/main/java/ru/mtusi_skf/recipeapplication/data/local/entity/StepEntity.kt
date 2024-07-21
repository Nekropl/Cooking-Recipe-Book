package ru.mtusi_skf.recipeapplication.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StepEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var instructionId: Int,
    val stepTitle: String,
    val number: Int,
    val ingredients: String,
    val equipments: String
)