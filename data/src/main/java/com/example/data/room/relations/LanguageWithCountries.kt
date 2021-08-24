package com.example.data.room.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.data.room.entity.CountryEntity
import com.example.data.room.entity.CountryLanguageCrossRefEntity
import com.example.data.room.entity.LanguageEntity

data class LanguageWithCountries(

    @Embedded
    val name: LanguageEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "countryName",
        associateBy = Junction(CountryLanguageCrossRefEntity::class)
    )
    val countries: MutableList<CountryEntity>
)