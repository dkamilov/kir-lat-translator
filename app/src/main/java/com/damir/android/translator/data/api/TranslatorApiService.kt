package com.damir.android.translator.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslatorApiService {

    @GET("translate?key=trnsl.1.1.20200524T110738Z.298d87602f92d59a.ccbce5dd0e9ae5b91db6719b6868142e2dfa6537")
    suspend fun translate(
        @Query("text")text: String,
        @Query("lang")lang: String
    ): Translation

    companion object {
        private const val BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/"

        private val logging = HttpLoggingInterceptor().apply{
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        fun create(): TranslatorApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
            return retrofit.create(TranslatorApiService::class.java)
        }
    }

}