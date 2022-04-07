package com.example.challenge4_afifuddin.database.recipe

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Resep(
    @PrimaryKey(autoGenerate = true) var id:Int?,
    @ColumnInfo(name = "nama") var name:String,
    @ColumnInfo(name = "bahan") var ingredient:String,
    @ColumnInfo(name = "cara") var step:String,

):Parcelable
