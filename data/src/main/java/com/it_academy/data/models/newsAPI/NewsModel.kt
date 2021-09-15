package com.it_academy.data.models.newsAPI

import com.google.gson.annotations.SerializedName

data class NewsModel(

    @SerializedName("sources")
    var sources: MutableList<SourceModel>

)