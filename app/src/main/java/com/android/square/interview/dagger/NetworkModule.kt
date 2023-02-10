package com.android.square.interview.dagger

import com.android.square.interview.network.RetrofitService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    @Provides
    fun provideService(): RetrofitService {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://s3.amazonaws.com/sq-mobile-interview/")
            .build()
        return retrofit.create(RetrofitService::class.java)
    }
}