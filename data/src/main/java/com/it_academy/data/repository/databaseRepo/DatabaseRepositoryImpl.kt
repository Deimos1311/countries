package com.it_academy.data.repository.databaseRepo

import com.it_academy.data.room.CountryDatabase
import com.it_academy.data.transformers.*
import com.it_academy.domain.dto.countries.CountryDTO
import com.it_academy.domain.dto.countries.CountryLanguageCrossRefDTO
import com.it_academy.domain.dto.countries.LanguageDTO
import com.it_academy.domain.repository.DatabaseRepository
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