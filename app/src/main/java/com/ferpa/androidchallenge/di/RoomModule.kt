package com.ferpa.androidchallenge.di

import android.content.Context
import androidx.room.Room
import com.ferpa.androidchallenge.data.local.CityDao
import com.ferpa.androidchallenge.data.local.CityRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideCityRoomDatabase(@ApplicationContext context: Context): CityRoomDatabase {
        return Room.databaseBuilder(
            context,
            CityRoomDatabase::class.java,
            CityRoomDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCityDao(cityRoomDatabase: CityRoomDatabase): CityDao {
        return cityRoomDatabase.cityDao()
    }
}
