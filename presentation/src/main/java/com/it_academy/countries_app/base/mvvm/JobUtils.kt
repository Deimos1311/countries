package com.it_academy.countries_app.base.mvvm

import androidx.lifecycle.MutableLiveData
import com.it_academy.domain.outcome.Outcome
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable

/**
 * Executes a job [Flowable] in IO thread and manages state of executing.
 */
fun <T> executeJob(job: Flowable<T>, outcome: MutableLiveData<Outcome<T>>): Disposable {
    outcome.loading(true)
    return job.executeOnIoThread()
        .subscribe({
            outcome.next(it)
        }, {
            outcome.failed(it)
        }, {
            if (outcome.value is Outcome.Next) {
                outcome.success((outcome.value as Outcome.Next).data)
            }
        })
}

fun <T> executeJobOnInitialThread(job: Flowable<T>, outcome: MutableLiveData<Outcome<T>>): Disposable {
    outcome.loading(true)
    return job.subscribe({
        outcome.next(it)
    }, {
        outcome.failed(it)
    }, {
        if (outcome.value is Outcome.Next) {
            outcome.success((outcome.value as Outcome.Next).data)
        }
    })
}

/**
 * Executes a job [Single] in IO thread and manages state of executing.
 */
fun <T> executeJob(job: Single<T>, outcome: MutableLiveData<Outcome<T>>): Disposable {
    outcome.loading(true)
    return job.executeOnIoThread()
        .subscribe({
            outcome.success(it)
        }, {
            outcome.failed(it)
        })
}

/**
 * Executes a job [Single] in IO thread and manages state of executing.
 */
/*fun executeJob(job: Completable, outcome: MutableLiveData<Outcome<Any>>): Disposable {
    outcome.loading(true)
    return job.executeOnIoThread()
        .subscribe({
            outcome.success(Any())
        }, {
            outcome.failed(it)
        })
}*/

fun <T> executeJobWithoutProgress(job: Flowable<T>, outcome: MutableLiveData<Outcome<T>>): Disposable {
    return job.executeOnIoThread()
        .subscribe({
            outcome.nextWithoutProgress(it)
        }, {
            outcome.failedWithoutProgress(it)
        }, {
            if (outcome.value is Outcome.Next) {
                outcome.successWithoutProgress((outcome.value as Outcome.Next).data)
            }
        })
}

/**
 * Extension function to push the loading status to the observing outcome
 * */
fun <T> MutableLiveData<Outcome<T>>.loading(isLoading: Boolean) {
    this.value = Outcome.loading(isLoading)
}

/**
 * Extension function to push  a success event with data to the observing outcome
 * */
fun <T> MutableLiveData<Outcome<T>>.success(t: T) {
    with(this) {
        loading(false)
        value = Outcome.success(t)
    }
}

/**
 * Extension function to push  a success event with data to the observing outcome without progress
 * */
fun <T> MutableLiveData<Outcome<T>>.successWithoutProgress(t: T) {
    with(this) {
        value = Outcome.success(t)
    }
}

/**
 * Extension function to push  a next event with data to the observing outcome
 * */
fun <T> MutableLiveData<Outcome<T>>.next(t: T) {
    with(this) {
        loading(false)
        value = Outcome.next(t)
    }
}

/**
 * Extension function to push  a next event with data to the observing outcome without progress
 * */
fun <T> MutableLiveData<Outcome<T>>.nextWithoutProgress(t: T) {
    with(this) {
        value = Outcome.next(t)
    }
}

/**
 * Extension function to push a failed event with an exception to the observing outcome
 * */
fun <T> MutableLiveData<Outcome<T>>.failed(e: Throwable) {
    with(this) {
        loading(false)
        value = Outcome.failure(e)
    }
}

/**
 * Extension function to push a failed event with an exception to the observing outcome without progress
 * */
fun <T> MutableLiveData<Outcome<T>>.failedWithoutProgress(e: Throwable) {
    with(this) {
        value = Outcome.failure(e)
    }
}
