package com.example.challenge4_afifuddin.database.recipe

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.challenge4_afifuddin.database.Login
import com.example.challenge4_afifuddin.database.LoginDao
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Resep::class ,Login::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun resepdao(): ResepDao
    abstract fun logindao():LoginDao

        companion object{
                var INSTANCE : AppDatabase? = null


                @InternalCoroutinesApi
                fun getInstance(context: Context): AppDatabase?{
                                if (INSTANCE == null){
                                synchronized(AppDatabase::class){
                                INSTANCE = Room.databaseBuilder(
                                context.applicationContext, AppDatabase::class.java, "App.db"
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