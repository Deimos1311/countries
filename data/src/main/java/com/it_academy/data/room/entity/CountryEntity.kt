package com.it_academy.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.it_academy.domain.NULL_INT_VALUE
import com.it_academy.domain.STRING_NULL_VALUE

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
