package com.example.data.network.retrofit_client

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private lateinit var interceptor: HttpLoggingInterceptor
    private lateinit var client: OkHttpClient.Builder

    fun getClient(baseURL: String): Retrofit? {

        addInterceptor()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(baseURL)
                .client(client.build())
                .build()
        }
        return retrofit
    }

    private fun addInterceptor() {
        interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client = OkHttpClient.Builder()
        client.addInterceptor(interceptor)
    }
}