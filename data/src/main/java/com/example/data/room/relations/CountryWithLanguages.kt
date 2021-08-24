package com.example.data.room.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.data.room.entity.CountryEntity
import com.example.data.room.entity.CountryLanguageCrossRefEntity
import com.example.data.room.entity.LanguageEntity

data class CountryWithLanguages(

    @Embedded
    val countryName: CountryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "countryName",
        associateBy = Junction(CountryLanguageCrossRefEntity::class)
    )
    val languages: MutableList<LanguageEntity>
)