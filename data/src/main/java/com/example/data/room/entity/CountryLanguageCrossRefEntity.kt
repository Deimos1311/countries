package com.example.data.room.entity

import androidx.room.Entity
import com.example.domain.STRING_NULL_VALUE

@Entity(primaryKeys = ["countryName", "languageName"], tableName = "cross_reference_table")
data class CountryLanguageCrossRefEntity(
    var countryName: String = STRING_NULL_VALUE,
    var languageName: String = STRING_NULL_VALUE
)