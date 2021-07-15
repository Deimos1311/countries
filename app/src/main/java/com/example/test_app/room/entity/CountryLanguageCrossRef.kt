package com.example.test_app.room.entity

import androidx.room.Entity

@Entity(primaryKeys = ["countryName", "name"])
data class CountryLanguageCrossRef(
    var countryName: String,
    var name: String
)