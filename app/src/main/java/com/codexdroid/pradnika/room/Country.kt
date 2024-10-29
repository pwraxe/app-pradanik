package com.codexdroid.pradnika.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(tableName = "table_country")
data class Country(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val code: String,
)

@Entity(tableName = "table_entered_mob_no")
data class TableSearchHistory(
    val id: Int,
    val mobile:String,
    val countryCode: String,
    val countryName: String,
    @PrimaryKey
    val timeInMilliSec: Long,
) {
    fun getTime() : String {
        //07 Sep 2023, 07:09 AM
        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm aa", Locale.getDefault())
        return simpleDateFormat.format(timeInMilliSec)
    }
}