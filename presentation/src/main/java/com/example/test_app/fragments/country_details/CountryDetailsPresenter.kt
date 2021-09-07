package com.example.test_app.fragments.country_details

import com.example.data.network.common.Common
import com.example.data.transformers.transformCountryModelMutableListToDto
import com.example.test_app.base.mvp.BaseMvpPresenter
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.withContext

class CountryDetailsPresenter : BaseMvpPresenter<CountryDetailsView>() {

    fun getCountryByName(countryName: String, isRefresh: Boolean) {

        addDisposable(
            inBackground(
                handleProgress(Common.retrofitService?.getCountryByName(countryName), isRefresh)
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
