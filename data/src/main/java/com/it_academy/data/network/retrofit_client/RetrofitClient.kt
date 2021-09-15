package com.it_academy.data.network.retrofit_client

import com.chenxyu.retrofit.adapter.FlowCallAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private lateinit var interceptor: HttpLoggingInterceptor
    private lateinit var client: OkHttpClient.Builder

    fun getRetrofitClient(baseURL: String): Retrofit? {
        addInterceptor()
        return getClient(RxJava3CallAdapterFactory.create(), baseURL)
    }

    fun getCoroutineRetrofitClient(baseURL: String): Retrofit? {
        addInterceptor()
        return getClient(CoroutineCallAdapterFactory.invoke(), baseURL)
    }

    fun getFlowRetrofitClient(baseURL: String): Retrofit? {
        addInterceptor()
        return getClient(FlowCallAdapterFactory(), baseURL)
    }

    private fun addInterceptor() {
        interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client = OkHttpClient.Builder()
        client.addInterceptor(interceptor)
    }

    private fun getClient(adapter: CallAdapter.Factory,  baseURL: String): Retrofit? {
            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(adapter)
                .baseUrl(baseURL)
                .client(client.build())
                .build()
        return retrofit
    }
}
