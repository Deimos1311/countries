package com.it_academy.data.interactor

import android.util.Log
import com.it_academy.domain.DEFAULT_VALUE_LONG
import com.it_academy.domain.HALF_MINUTE_MILLIS
import com.it_academy.domain.dto.countries.CountryDTO
import com.it_academy.domain.interactor.BaseInteractor
import com.it_academy.domain.interactor.CountryInteractor
import com.it_academy.domain.repository.CacheRepository
import com.it_academy.domain.repository.DatabaseRepository
import com.it_academy.domain.repository.NetworkRepository
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*

class CountryInteractorImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
    private val cacheRepository: CacheRepository
) : BaseInteractor(), CountryInteractor {

    private var lastNetworkRequestTime: Long = DEFAULT_VALUE_LONG
    private val countrySubject = PublishSubject.create<MutableList<CountryDTO>>()

    override fun getCountryChannel(): Flowable<MutableList<CountryDTO>> =
        countrySubject.toFlowable(BackpressureStrategy.LATEST)

    override fun requestAllCountries(): Flowable<Any> {
//todo get data from DB when app first start
        return inBackground(
            getTimeSinceLastUpdate()
                .flatMap { isMoreThanMinute ->
                    return@flatMap if (isMoreThanMinute) {
                        lastNetworkRequestTime = System.currentTimeMillis()
                        networkRepository.getCountryDate()
                            .flatMap {
                                it.forEach { item ->
                                    databaseRepository.addLanguage(item.languages)
                                }
                                databaseRepository.addAllCountries(it)
                                cacheRepository.addAllCountries(it)
                            }
                    } else {
                        Thread.sleep(300)
                        cacheRepository.getAllCountries()
                    }
                }
                .doOnNext {
                    Log.e("HZ", "$it")
                    countrySubject.onNext(it)
                }
                .map { Any() })
    }

    override fun requestCountriesByName(name: String): Flowable<Any> {
        return getTimeSinceLastUpdate()
            .flatMap { isMoreThanMinute ->
                return@flatMap if (isMoreThanMinute) {
                    lastNetworkRequestTime = System.currentTimeMillis()
                    networkRepository.getCountryByName(name)
                        .flatMap { cacheRepository.addFilteredCountries(it) }
                } else {
                    cacheRepository.getAllCountries()
                        .map {
                            it.filter { item ->
                                item.countryName.contains(name)
                            }.toMutableList()
                        }
                }
            }
            .doOnNext {
                countrySubject.onNext(it)
            }
            .map { Any() }
    }

    private fun getTimeSinceLastUpdate() =
        Flowable.just(
            lastNetworkRequestTime == 0L
                    || System.currentTimeMillis() - lastNetworkRequestTime > HALF_MINUTE_MILLIS
        )
}
