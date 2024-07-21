package ru.mtusi_skf.recipeapplication.data.local.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import ru.mtusi_skf.recipeapplication.data.local.dao.RecipeDao
import ru.mtusi_skf.recipeapplication.data.local.entity.InstructionEntity
import ru.mtusi_skf.recipeapplication.data.local.entity.RecipeEntity
import ru.mtusi_skf.recipeapplication.data.local.entity.StepEntity

@Database(entities = [RecipeEntity::class, InstructionEntity::class, StepEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}