package com.example.bonialbrochures.domain.repository

import com.example.bonialbrochures.data.local.model.BrochureEntity
import com.example.bonialbrochures.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface BrochuresRepository {
    suspend fun getBrochuresList(): Flow<Resource<List<BrochureEntity>>>
    suspend fun getFilteredBrochures(): Flow<Resource<List<BrochureEntity>>>
}