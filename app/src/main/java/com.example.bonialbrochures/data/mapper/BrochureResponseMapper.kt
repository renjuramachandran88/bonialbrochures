package com.example.bonialbrochures.data.mapper

import com.example.bonialbrochures.data.entity.Contents
import com.example.bonialbrochures.data.local.model.BrochureEntity
import javax.inject.Inject

class BrochureResponseMapper @Inject constructor() {

    fun mapNetworkResponseToLocalEntity(contentsList: List<Contents>?): List<BrochureEntity> {
        val networkBrochures = mutableListOf<BrochureEntity>()
        contentsList?.forEach { contentsData ->

            if (contentsData.contentType == "brochurePremium" || contentsData.contentType == "brochure") {

                contentsData.brochureContent?.content?.forEach { content ->
                    val brochureData = dtoToEntity(contentsData, content)

                    networkBrochures.add(brochureData)

                }
            }
        }
        return networkBrochures
    }
}