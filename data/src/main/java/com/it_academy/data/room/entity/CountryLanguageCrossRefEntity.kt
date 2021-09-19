package com.it_academy.data.room.entity

import androidx.room.Entity
import com.it_academy.domain.STRING_NOT_AVAILABLE

@Entity(primaryKeys = ["countryName", "languageName"], tableName = "cross_reference_table")
data class CountryLanguageCrossRefEntity(
    var countryName: String = STRING_NOT_AVAILABLE,
    var languageName: String = STRING_NOT_AVAILABLE
)