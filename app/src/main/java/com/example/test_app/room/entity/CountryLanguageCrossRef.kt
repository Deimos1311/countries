package com.example.test_app.room.entity

import androidx.room.Entity

@Entity(primaryKeys = ["countryName", "name"], tableName = "cross_reference_table")
data class CountryLanguageCrossRef(
    var countryName: String,
    var name: String
)