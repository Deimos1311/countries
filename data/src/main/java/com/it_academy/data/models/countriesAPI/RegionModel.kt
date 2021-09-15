package com.it_academy.data.models.countriesAPI

import com.google.gson.annotations.SerializedName

data class RegionModel(

    @SerializedName("region")
    var region: String?
)