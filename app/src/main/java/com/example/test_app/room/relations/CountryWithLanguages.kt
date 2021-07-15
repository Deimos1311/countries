package com.example.test_app.room.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity

data class CountryWithLanguages(

    @Embedded
    val countryName: CountryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "countryName",
        associateBy = Junction(CountryLanguageCrossRef::class)
    )
    val languages: MutableList<LanguagesListEntity>
)