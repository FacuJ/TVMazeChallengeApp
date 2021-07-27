package com.facundojaton.tvmazechallenge.di

import com.facundojaton.tvmazechallenge.remote.APIConstants
import com.facundojaton.tvmazechallenge.remote.SeriesService
import com.facundojaton.tvmazechallenge.repository.SeriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataModule {

    @Provides
    fun provideBaseUrl() = APIConstants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        OkHttpClient
            .Builder()
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL : String) : Retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideSeriesService(retrofit: Retrofit): SeriesService = retrofit.create(SeriesService::class.java)

    @Provides
    @Singleton
    fun provideSeriesRepository(service: SeriesService) = SeriesRepository(service)
}