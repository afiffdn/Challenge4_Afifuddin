package com.example.challenge4_afifuddin.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface LoginDao {
    @Query("SELECT * FROM Login")
    fun getAllLogin():List<Login>
    @Insert(onConflict = REPLACE)
    fun insertLogin(login: Login):Long
    @Update
    fun updateLogin(login: Login):Int
    @Delete
    fun deleteLogin(login: Login):Int
    @Query("SELECT EXISTS( SELECT * FROM login WHERE username = :username AND password = :password )")
    fun checkLogin(username:String, password:String):Boolean

}