package com.example.test_app.base.mvp

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseMvpPresenter<ViewType : BaseMvpView> {

    private var view: ViewType? = null
    private val compositeDisposable = CompositeDisposable()

    fun attachView(viewType: ViewType) {
        view = viewType
    }

    fun detachView() {
        view = null
    }
    protected open fun getView(): ViewType? = view

    fun addDisposable(disposable: Disposable?) {
        compositeDisposable.add(disposable)
    }

    fun <Data> inBackground(flowable: Flowable<Data>?): Flowable<Data>? {
        return flowable?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
    }

    fun <Data> handleProgress(flowable: Flowable<Data>?, isRefresh: Boolean): Flowable<Data>? {
        return flowable
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSubscribe {
                if (!isRefresh) {
                    getView()?.showProgress()
                }
            }
            ?.doOnNext {
                getView()?.hideProgress()
            }
            ?.observeOn(Schedulers.io())
    }

    fun onDestroyView() {
        compositeDisposable.clear()
    }
}
