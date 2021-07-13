package com.example.test_app.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.test_app.model.LanguagesList

@Entity(tableName = "countries_table")
data class EntityCountry(

    @ColumnInfo(name = "countryName")
    val countryName: String,

    @ColumnInfo(name = "cityName")
    val cityName: String,

    @ColumnInfo(name = "population")
    val population: Int,

    @ColumnInfo(name = "flag")
    val flag: String,

/*    @ColumnInfo(name = "languages")
    val languages: MutableList<LanguagesList>*/

){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
