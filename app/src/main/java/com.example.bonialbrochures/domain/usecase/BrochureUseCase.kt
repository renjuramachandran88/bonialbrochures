package com.example.bonialbrochures.domain.usecase

import com.example.bonialbrochures.data.local.model.BrochureEntity
import com.example.bonialbrochures.domain.repository.BrochuresRepository
import com.example.bonialbrochures.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BrochureUseCase @Inject constructor(
    private val brochuresRepository: BrochuresRepository
) {

    suspend fun getBrochureList(): Flow<Resource<List<BrochureEntity>>> {
        return brochuresRepository.getBrochuresList()
    }

    suspend fun getFilteredBrochureList(): Flow<Resource<List<BrochureEntity>>> =
        brochuresRepository.getFilteredBrochures()
}