package com.example.bonialbrochures.di

import com.example.bonialbrochures.data.local.BrochureDatabase
import com.example.bonialbrochures.data.mapper.BrochureResponseMapper
import com.example.bonialbrochures.data.remote.BrochureService
import com.example.bonialbrochures.data.repository.BrochuresRepositoryImpl
import com.example.bonialbrochures.domain.repository.BrochuresRepository
import com.example.bonialbrochures.domain.usecase.BrochureUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideBrochureRepository(
        brochureService: BrochureService,
        brochureDatabase: BrochureDatabase,
        mapper: BrochureResponseMapper
    ): BrochuresRepository {
        return BrochuresRepositoryImpl(brochureService, brochureDatabase, mapper)
    }

    @Provides
    @ViewModelScoped
    fun providesBrochureUseCase(repository: BrochuresRepository): BrochureUseCase =
        BrochureUseCase(repository)
}
