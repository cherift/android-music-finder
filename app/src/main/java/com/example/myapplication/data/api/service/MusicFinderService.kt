package com.example.myapplication.data.api.service

import com.example.myapplication.data.api.model.MusicFinderResponse
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicFinderService {

    @GET("find")
    fun searchMusics(@Query("text") text : String) : Single<MusicFinderResponse>

    @GET("listen")
    fun getMusicLink(@Query("artist") artist : String, @Query("title") title : String)

    companion object {
        operator fun invoke() : MusicFinderService {
            val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor()

            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client : OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl("http://lyrics-extractor.herokuapp.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()
                .create(MusicFinderService::class.java)
        }
    }
}