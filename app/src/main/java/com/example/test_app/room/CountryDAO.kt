package com.example.test_app.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.test_app.base.mvvm.Outcome
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity
import io.reactivex.rxjava3.core.Flowable

@Dao
interface CountryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllCountries(mutableList: MutableList<CountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLanguage(mutableList: MutableList<LanguagesListEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountryLanguageCrossRef(mutableList: MutableList<CountryLanguageCrossRef>)

    @Transaction
    @Query("SELECT * FROM countries_table")
    fun getAllCountries(): Flowable<MutableList<CountryEntity>> //LiveData<MutableList<CountryEntity>>

    @Transaction
    @Query("SELECT * FROM languages_table")
    fun getLanguages(): Flowable<MutableList<LanguagesListEntity>>

    @Transaction
    @Query("SELECT * FROM cross_reference_table")
    fun getCrossRef(): LiveData<MutableList<CountryLanguageCrossRef>>

/*    @Transaction
    @Query("SELECT * FROM countries_table WHERE countryName = :countryName")
    suspend fun getLanguagesOfCountry(countryName: String): MutableList<CountryWithLanguages>

    @Transaction
    @Query("SELECT * FROM languages_table WHERE name = :name")
    suspend fun getCountriesOfLanguage(name: String): MutableList<LanguageWithCountries>*/
}
