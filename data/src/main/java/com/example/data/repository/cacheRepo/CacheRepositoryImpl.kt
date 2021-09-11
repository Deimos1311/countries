package com.example.data.repository.cacheRepo

import com.example.domain.dto.CountryDTO
import com.example.domain.repository.CacheRepository
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableEmitter

class CacheRepositoryImpl : CacheRepository {

    private val cacheList: MutableList<CountryDTO> = mutableListOf()

    override fun addAllCountries(list: MutableList<CountryDTO>): Flowable<MutableList<CountryDTO>> {
        return Flowable.create({
            updateList(cacheList, list, it)
        }, BackpressureStrategy.LATEST)
    }

    override fun addFilteredCountries(list: MutableList<CountryDTO>): Flowable<MutableList<CountryDTO>> {
        TODO("Not yet implemented")
    }

    override fun getAllCountries(): Flowable<MutableList<CountryDTO>> {
        return Flowable.just(cacheList)
    }

    override fun getFilteredCountries(): Flowable<MutableList<CountryDTO>> {
        TODO("Not yet implemented")
    }

    private fun updateList(
        currentList: MutableList<CountryDTO>,
        list: MutableList<CountryDTO>,
        it: FlowableEmitter<MutableList<CountryDTO>>
    ) {
        currentList.clear()
        currentList.addAll(list)
        it.onNext(list)
        it.onComplete()
    }
}
