package com.example.bonialbrochures.data.remote

import com.example.bonialbrochures.data.entity.BrochureResponse
import retrofit2.Response
import retrofit2.http.GET

interface BrochureService {
    @GET("/stories-test/shelf.json")
    suspend fun getBrochuresList(): Response<BrochureResponse>
}