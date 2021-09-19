package com.it_academy.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.it_academy.domain.INT_NOT_AVAILABLE
import com.it_academy.domain.STRING_NOT_AVAILABLE

@Entity(tableName = "countries_table")
data class CountryEntity(

    @PrimaryKey
    @ColumnInfo(name = "countryName")
    var countryName: String = STRING_NOT_AVAILABLE,
    @ColumnInfo(name = "cityName")
    var cityName: String = STRING_NOT_AVAILABLE,
    @ColumnInfo(name = "population")
    var population: Int = INT_NOT_AVAILABLE

)
