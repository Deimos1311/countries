package com.it_academy.data.room.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.it_academy.data.room.entity.CountryEntity
import com.it_academy.data.room.entity.CountryLanguageCrossRefEntity
import com.it_academy.data.room.entity.LanguageEntity

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