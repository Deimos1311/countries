package com.example.test_app.room

import androidx.room.*
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity
import com.example.test_app.room.relations.CountryWithLanguages
import com.example.test_app.room.relations.LanguageWithCountries

@Dao
interface CountryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    /*suspend */fun addAllCountries(mutableList: MutableList<CountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    /*suspend*/ fun addLanguage(mutableList: MutableList<LanguagesListEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    /*suspend*/ fun insertCountryLanguageCrossRef(mutableList: MutableList<CountryLanguageCrossRef>)

    @Transaction
    @Query("SELECT * FROM countries_table")
    /*suspend*/ fun getAllCountries(): MutableList<CountryEntity>

/*    @Transaction
    @Query("SELECT * FROM countries_table WHERE countryName = :countryName")
    suspend fun getLanguagesOfCountry(countryName: String): MutableList<CountryWithLanguages>

    @Transaction
    @Query("SELECT * FROM languages_table WHERE name = :name")
    suspend fun getCountriesOfLanguage(name: String): MutableList<LanguageWithCountries>*/
}
