package com.example.test_app.fragments.map

import com.example.data.network.common.Common
import com.example.data.transformers.transformCountryModelMutableListToDto
import com.example.test_app.R
import com.example.test_app.base.mvp.BaseMvpPresenter
import com.google.android.gms.maps.model.LatLng

class MapPresenter : BaseMvpPresenter<MapView>() {

    fun getMap(isRefresh: Boolean) {

        addDisposable(
            inBackground(
                handleProgress(Common.retrofitService?.getCountryDate(), isRefresh)
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