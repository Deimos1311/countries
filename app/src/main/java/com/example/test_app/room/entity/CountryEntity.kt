package com.example.test_app.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries_table")
data class CountryEntity(

    @PrimaryKey
    @ColumnInfo(name = "countryName")
    val countryName: String,
    @ColumnInfo(name = "cityName")
    val cityName: String,
    @ColumnInfo(name = "population")
    val population: Int,
    @ColumnInfo(name = "flag")
    val flag: String,
)
