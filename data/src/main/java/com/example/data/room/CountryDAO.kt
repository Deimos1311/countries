package com.example.data.room

import androidx.room.*
import com.example.data.room.entity.CountryEntity
import com.example.data.room.entity.CountryLanguageCrossRefEntity
import com.example.data.room.entity.LanguageEntity
import com.example.domain.dto.CountryDTO
import io.reactivex.rxjava3.core.Flowable

@Dao
interface CountryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllCountries(mutableList: MutableList<CountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLanguage(mutableList: MutableList<LanguageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountryLanguageCrossRef(mutableList: MutableList<CountryLanguageCrossRefEntity>)

    @Transaction
    @Query("SELECT * FROM countries_table")
    fun getAllCountries(): Flowable<MutableList<CountryEntity>> //LiveData<MutableList<CountryEntity>>

    @Transaction
    @Query("SELECT * FROM languages_table")
    fun getLanguages(): Flowable<MutableList<LanguageEntity>>

    @Transaction
    @Query("SELECT * FROM cross_reference_table")
    fun getCrossRef(): Flowable<MutableList<CountryLanguageCrossRefEntity>> //LiveData<MutableList<CountryLanguageCrossRef>>

}
