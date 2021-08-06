package com.example.test_app.fragments.map

import com.example.test_app.R
import com.example.test_app.base.mvp.BaseMvpPresenter
import com.example.test_app.common.Common
import com.example.test_app.transformers.transformToMutableList
import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.core.Flowable

class MapPresenter : BaseMvpPresenter<MapView>() {

    fun getMap() {

        addDisposable(
            inBackground(Common.retrofitService?.getCountryDate()
               /* ?.onErrorResumeNext {
                    Flowable.just(mutableListOf())
                }*/)
                ?.map { it.transformToMutableList() }
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