package com.example.test_app.fragments.country_details

import com.example.test_app.base.mvp.BaseMvpPresenter
import com.example.data.transformers.transformCountryModelMutableListToDto
import com.google.android.gms.maps.model.LatLng

class CountryDetailsPresenter : BaseMvpPresenter<CountryDetailsView>() {

    fun getCountryByName(countryName: String, isRefresh: Boolean) {

        addDisposable(
            inBackground(
                handleProgress(com.example.data.network.common.Common.retrofitService?.getCountryByName(countryName), isRefresh)
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

/*        Flowable
            .just(
                Country(
                    "",
                    "",
                    0,
                    mutableListOf(),
                    "",
                    0.0,
                    mutableListOf()
                )
            )
            .subscribe({
                getView()?.showCountryInfo()
            }, {

            })*/
    }
}
