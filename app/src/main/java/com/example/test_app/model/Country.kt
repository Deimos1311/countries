package com.example.test_app.model

import com.google.gson.annotations.SerializedName

data class Country(

    @SerializedName("name")
    var countryName: String? = null,

    @SerializedName("capital")
    var cityName: String? = null,

    var population: Int? = null,
    var languages: MutableList<LanguagesList>? = null,
    var flag: String? = null
)