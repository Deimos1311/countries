package com.example.test_app.model

import androidx.room.TypeConverters
/*
import com.example.test_app.LanguageConverter
*/
import com.google.gson.annotations.SerializedName

data class Country(

    @SerializedName("name")
    var countryName: String,

    @SerializedName("capital")
    var cityName: String,

    var population: Int,

/*    @TypeConverters(LanguageConverter::class)
    var languages: MutableList<LanguagesList>,*/

    var flag: String
)