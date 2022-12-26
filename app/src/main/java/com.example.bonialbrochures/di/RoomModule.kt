package com.example.bonialbrochures.di

import android.content.Context
import androidx.room.Room
import com.example.bonialbrochures.data.local.BrochureDao
import com.example.bonialbrochures.data.local.BrochureDatabase
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
    fun provideBrochureDatabase(@ApplicationContext context: Context): BrochureDatabase {
        return Room.databaseBuilder(
            context,
            BrochureDatabase::class.java,
            "BROCHURE_DATABASE"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideBrochureDao(brochureDatabase: BrochureDatabase): BrochureDao {
        return brochureDatabase.brochureDao()
    }
}