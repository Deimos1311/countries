package com.example.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.NULL_INT_VALUE
import com.example.domain.STRING_NULL_VALUE

@Entity(tableName = "countries_table")
data class CountryEntity(

    @PrimaryKey
    @ColumnInfo(name = "countryName")
    var countryName: String = STRING_NULL_VALUE,
    @ColumnInfo(name = "cityName")
    var cityName: String = STRING_NULL_VALUE,
    @ColumnInfo(name = "population")
    var population: Int = NULL_INT_VALUE

)
