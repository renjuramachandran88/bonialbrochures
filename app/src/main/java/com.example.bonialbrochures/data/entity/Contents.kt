package com.example.bonialbrochures.data.entity

import com.google.gson.annotations.SerializedName

data class Contents(
    val placement: String,
    val adFormat: String? = null,
    val contentFormatSource: String,
    val contentType: String,
    val externalTracking: ExternalTracking,
    @SerializedName("content")
    val brochureContent: ContentList?,
)


