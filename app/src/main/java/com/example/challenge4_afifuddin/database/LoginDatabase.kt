package com.example.challenge4_afifuddin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [Login::class], version = 1)
abstract class LoginDatabase : RoomDatabase(){
    abstract fun loginDao() : LoginDao

    companion object{
        private var INSTANCE : LoginDatabase? = null



        fun getInstance(context: Context): LoginDatabase? {
            if (INSTANCE == null){
                synchronized(LoginDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LoginDatabase::class.java,
                        "Login.db"
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