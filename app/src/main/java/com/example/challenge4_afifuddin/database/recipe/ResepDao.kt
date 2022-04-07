package com.example.challenge4_afifuddin.database.recipe

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface ResepDao {
@Query("SELECT*FROM Resep")
fun getAllRecipe():List<Resep>

@Insert(onConflict = REPLACE)
fun insertRecipe(resep: Resep): Long

@Update
fun updateRecipe(resep: Resep):Int

@Delete
fun deleteRecipe(resep: Resep):Int


}