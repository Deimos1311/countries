package com.example.test_app.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val countryName: String,
    @SerializedName("capital")
    var cityName: String,
    @SerializedName("population")
    var population: Int,
    @SerializedName("languages")
    var languages: MutableList<LanguagesList>,
    @SerializedName("flag")
    var flag: String,
    @SerializedName("area")
    var area: Double,
    @SerializedName("latlng")
    var location: MutableList<Double>
)
