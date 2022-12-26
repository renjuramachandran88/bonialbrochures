package com.example.bonialbrochures.data.entity

import com.google.gson.annotations.SerializedName

data class BrochureResponse(
    @SerializedName("_embedded")
    val embedded: Embedded,
    @SerializedName("_links")
    val links: Links,
    val page: Page
)
