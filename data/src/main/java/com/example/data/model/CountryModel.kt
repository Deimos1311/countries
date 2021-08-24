package com.example.data.model

import com.google.gson.annotations.SerializedName

data class CountryModel(
    @SerializedName("name")
    val countryName: String?,
    @SerializedName("capital")
    var cityName: String?,
    @SerializedName("population")
    var population: Int?,
    @SerializedName("languages")
    var languages: MutableList<LanguageModel>?,
    @SerializedName("flag")
    var flag: String?,
    @SerializedName("area")
    var area: Double?,
    @SerializedName("latlng")
    var location: MutableList<Double>?
)
