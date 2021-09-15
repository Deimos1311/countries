package com.it_academy.countries_app.fragments.country_details

import com.google.android.gms.maps.model.LatLng
import com.it_academy.countries_app.base.mvp.BaseMvpPresenter
import com.it_academy.data.network.common_countries.CommonCountries
import com.it_academy.data.transformers.transformCountryModelMutableListToDto

class CountryDetailsPresenter : BaseMvpPresenter<CountryDetailsView>() {

    fun getCountryByName(countryName: String, isRefresh: Boolean) {

        addDisposable(
            inBackground(
                handleProgress(CommonCountries.retrofitService?.getCountryByName(countryName), isRefresh)
            )
                ?.map { it.transformCountryModelMutableListToDto() }
                ?.subscribe({
                    getView()?.showCountryInfo(
                        it[0],
                        LatLng(
                            it[0].location[0],
                            it[0].location[1]
                        )
                    )
                }, { throwable ->
                    throwable.printStackTrace()
                    getView()?.internetError()
                })
        )
    }
}
