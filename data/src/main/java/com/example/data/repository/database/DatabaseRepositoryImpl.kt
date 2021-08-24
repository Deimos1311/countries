package com.example.data.repository.database

import com.example.data.room.CountryDatabase
import com.example.data.transformers.*
import com.example.domain.dto.CountryDTO
import com.example.domain.dto.CountryLanguageCrossRefDTO
import com.example.domain.dto.LanguageDTO
import com.example.domain.repository.DatabaseRepository
import io.reactivex.rxjava3.core.Flowable

class DatabaseRepositoryImpl(private val database: CountryDatabase) :
    DatabaseRepository {
    override fun addAllCountries(mutableList: MutableList<CountryDTO>) =
        database.countryDAO.addAllCountries(mutableList.transformCountryDtoMutableListToEntity())

    override fun addLanguage(mutableList: MutableList<LanguageDTO>) =
        database.countryDAO.addLanguage(mutableList.transformLanguageDtoMutableListToEntity())

    override fun insertCountryLanguageCrossRef(
        mutableList: MutableList<CountryLanguageCrossRefDTO>
    ) {
        database.countryDAO.insertCountryLanguageCrossRef(
            mutableList
                .transformCountryLanguageCrossRefDtoToEntity()
        )
    }

    override fun getAllCountries(): Flowable<MutableList<CountryDTO>> =
        database.countryDAO.getAllCountries().map { it.transformCountryEntityMutableListToDto() }

    override fun getLanguages(): Flowable<MutableList<LanguageDTO>> =
        database.countryDAO.getLanguages().map { it.transformLanguageEntityMutableListToDto() }

    override fun getCrossRef(): Flowable<MutableList<CountryLanguageCrossRefDTO>> =
        database.countryDAO.getCrossRef()
            .map { it.transformCountryLanguageCrossRefMutableListYoDto() }
}


/*



    override fun insertCountryLanguageCrossRef(mutableList: MutableList<CountryLanguageCrossRef>) =
        database.countryDAO.insertCountryLanguageCrossRef(mutableList)

    override fun getAllCountries(): Flowable<MutableList<CountryEntity>> =
        database.countryDAO.getAllCountries()

    override fun getLanguages(): Flowable<MutableList<LanguagesListEntity>> =
        database.countryDAO.getLanguages()

    override fun getCrossRef(): LiveData<MutableList<CountryLanguageCrossRef>> =
        database.countryDAO.getCrossRef()*/
//}