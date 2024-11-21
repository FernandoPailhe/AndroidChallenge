package com.ferpa.androidchallenge.di

import com.ferpa.androidchallenge.data.local.CityDao
import com.ferpa.androidchallenge.data.repository.CityRepositoryImpl
import com.ferpa.androidchallenge.domain.repository.CityRepository
import com.ferpa.androidchallenge.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCityRepository(
        cityDao: CityDao,
        citySource: ApiService
    ): CityRepository = CityRepositoryImpl(
        cityDao,
        citySource
    )

}