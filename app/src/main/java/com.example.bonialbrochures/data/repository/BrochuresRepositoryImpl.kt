package com.example.bonialbrochures.data.repository

import androidx.room.withTransaction
import com.example.bonialbrochures.data.local.BrochureDatabase
import com.example.bonialbrochures.data.local.model.BrochureEntity
import com.example.bonialbrochures.data.mapper.BrochureResponseMapper
import com.example.bonialbrochures.data.remote.BrochureService
import com.example.bonialbrochures.domain.repository.BrochuresRepository
import com.example.bonialbrochures.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BrochuresRepositoryImpl @Inject constructor(
    private val brochureService: BrochureService,
    private val database: BrochureDatabase,
    private val mapper: BrochureResponseMapper
) : BrochuresRepository {
    override suspend fun getBrochuresList(): Flow<Resource<List<BrochureEntity>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = getBrochuresFromNetwork()

                database.withTransaction {
                    database.brochureDao().deleteBrochures()
                    database.brochureDao().insertBrochures(response)
                }

                emit(Resource.Success(data = response))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
            }
        }
    }

    override suspend fun getFilteredBrochures(): Flow<Resource<List<BrochureEntity>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val filteredBrochures = database.brochureDao().getFilteredBrochures()
                emit(Resource.Success(filteredBrochures))
            } catch (e: Exception) {
                emit(Resource.Error("There was a problem getting the filtered data: $e"))
            }
        }
    }


    private suspend fun getBrochuresFromNetwork(): List<BrochureEntity> {
        val networkResponse = brochureService.getBrochuresList()
        if (networkResponse.isSuccessful) {
            val brochuresResponse = networkResponse.body()?.embedded?.contentsList

            return mapper.mapNetworkResponseToLocalEntity(brochuresResponse)
        }
        return emptyList()
    }
}