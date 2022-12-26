package com.example.bonialbrochures.data.mapper

import com.example.bonialbrochures.data.local.model.BrochureEntity
import com.example.bonialbrochures.data.entity.BrochureContent
import com.example.bonialbrochures.data.entity.Contents


fun dtoToEntity(
    contentsData: Contents,
    content: BrochureContent
): BrochureEntity {
    return BrochureEntity(
        id = content.id,
        contentType = contentsData.contentType,
        brochureImage = content.brochureImage,
        retailerName = content.retailer?.retailerName,
        distance = content.distance
    )
}