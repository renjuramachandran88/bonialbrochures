package com.example.bonialbrochures.data.repository

import app.cash.turbine.test
import com.example.bonialbrochures.data.entity.BrochureResponse
import com.example.bonialbrochures.data.local.BrochureDatabase
import com.example.bonialbrochures.data.local.model.BrochureEntity
import com.example.bonialbrochures.data.mapper.BrochureResponseMapper
import com.example.bonialbrochures.data.remote.BrochureService
import com.example.bonialbrochures.data.testutils.MainCoroutineRule
import com.example.bonialbrochures.domain.util.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class BrochuresRepositoryImplTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var subject: BrochuresRepositoryImpl

    private lateinit var brochureService: BrochureService

    private lateinit var database: BrochureDatabase

    private lateinit var mapper: BrochureResponseMapper

    private lateinit var brochureEntity: BrochureEntity

    private lateinit var brochureResponse: Response<BrochureResponse>

    @Before
    fun setUp() {
        brochureService = mockk()
        database = mockk()
        mapper = mockk()
        brochureEntity = mockk()
        brochureResponse = mockk()

        subject = BrochuresRepositoryImpl(brochureService, database, mapper)
    }

    @Test
    fun getBrochureList_givenApiSuccess_returnsListOfBrochureEntity() = runTest {
        coEvery { brochureService.getBrochuresList() } returns brochureResponse

        every { mapper.mapNetworkResponseToLocalEntity(brochureResponse.body()?.embedded?.contentsList) } returns (listOf(
            brochureEntity
        ))

        subject.getBrochuresList().test {
            assertThat(awaitItem()).isInstanceOf(Resource.Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(Resource.Success::class.java)
            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun getBrochureList_givenApiFailure_returnsListOfBrochureEntity() = runTest {
        val errorResponse =
            "{\n" +
                    "  \"type\": \"error\",\n" +
                    "  \"message\": \"What you were looking for isn't here.\"\n" +
                    "}"
        val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())

        coEvery { brochureService.getBrochuresList() } returns Response.error(
            500,
            errorResponseBody
        )

        subject.getBrochuresList().test {
            assertThat(awaitItem()).isInstanceOf(Resource.Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(Resource.Error::class.java)
            cancelAndConsumeRemainingEvents()
        }

    }
}