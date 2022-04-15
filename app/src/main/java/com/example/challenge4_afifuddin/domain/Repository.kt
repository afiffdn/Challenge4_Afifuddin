package com.example.challenge4_afifuddin.domain

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.challenge4_afifuddin.database.recipe.AppDatabase
import com.example.challenge4_afifuddin.database.recipe.Resep
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class Repository(context: Context) {


private val dB = AppDatabase.getInstance(context)

@WorkerThread
    suspend fun getAllRecipe(): List<Resep>?{
    return dB?.resepdao()?.getAllRecipe()
    }
    suspend fun insert(resep: Resep):Long?{
    return dB?.resepdao()?.insertRecipe(resep)
    }
    suspend fun update(resep: Resep):Int?{
        return dB?.resepdao()?.updateRecipe(resep)
    }
    suspend fun delete(resep: Resep):Int?{
        return dB?.resepdao()?.updateRecipe(resep)
    }


}