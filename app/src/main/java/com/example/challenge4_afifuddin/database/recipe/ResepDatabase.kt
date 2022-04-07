package com.example.challenge4_afifuddin.database.recipe

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Resep::class], version = 1)
abstract class ResepDatabase :RoomDatabase(){
    abstract fun resepdao(): ResepDao

        companion object{
                var INSTANCE : ResepDatabase? = null


                @InternalCoroutinesApi
                fun getInstance(context: Context): ResepDatabase?{
                                if (INSTANCE == null){
                                synchronized(ResepDatabase::class){
                                INSTANCE = Room.databaseBuilder(
                                context.applicationContext, ResepDatabase::class.java, "Resep.db"
                                ).build()

                                }

                                }
                            return INSTANCE
                        }
                fun destroyInstance(){
                    INSTANCE = null
                }
                }
}