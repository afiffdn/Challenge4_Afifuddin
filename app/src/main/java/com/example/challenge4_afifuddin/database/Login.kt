package com.example.challenge4_afifuddin.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Login(
    @PrimaryKey @ColumnInfo(name = "username") var username:String,
    @ColumnInfo(name="password") var password:String,
    @ColumnInfo(name = "email") var date:String,
    @ColumnInfo(name = "Nama") var nama: String,
):Parcelable