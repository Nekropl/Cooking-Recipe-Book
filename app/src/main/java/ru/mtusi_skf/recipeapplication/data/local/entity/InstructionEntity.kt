package ru.mtusi_skf.recipeapplication.data.local.entity
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["recipeId"], unique = true)])
data class InstructionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var recipeId: Int
)