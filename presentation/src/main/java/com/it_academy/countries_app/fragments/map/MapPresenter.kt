package com.it_academy.countries_app.fragments.map

import com.google.android.gms.maps.model.LatLng
import com.it_academy.countries_app.R
import com.it_academy.countries_app.base.mvp.BaseMvpPresenter
import com.it_academy.data.network.common_countries.CommonCountries
import com.it_academy.data.transformers.transformCountryModelMutableListToDto

class MapPresenter : BaseMvpPresenter<MapView>() {

    fun getMap(isRefresh: Boolean) {

        addDisposable(
            inBackground(
                handleProgress(CommonCountries.retrofitService?.getCountryDate(), isRefresh)
            )
                ?.map { it.transformCountryModelMutableListToDto() }
                ?.subscribe({
                    it.forEach { item ->
                        if (item.location.size == 2) {
                            getView()?.showMap(
                                LatLng(
                                    item.location[0],
                                    item.location[1]
                                )
                            )
                        }
                    }
                }, { throwable ->
                    throwable.printStackTrace()
                    getView()?.showError(R.string.connect_error_message.toString())
                })
        )
    }
}