package com.mezsoft.geosol.data.dbLocal.di

import android.content.Context
import androidx.room.Room
import com.mezsoft.geosol.common.Constants
import com.mezsoft.geosol.data.dbLocal.AppDatabase
import com.mezsoft.geosol.data.dbLocal.dao.PlantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providePlantDao(appDatabase: AppDatabase): PlantDao {
        return appDatabase.plantDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, Constants.geoDb)
            .fallbackToDestructiveMigration() .build()
    }





}


