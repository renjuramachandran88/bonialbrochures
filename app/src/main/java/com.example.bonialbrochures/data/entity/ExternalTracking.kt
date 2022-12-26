package com.example.bonialbrochures.data.entity

import com.google.gson.annotations.SerializedName

data class ExternalTracking(
    @SerializedName("impression")
    val impressionList: List<String> = emptyList(),
    @SerializedName("click")
    val clickList: List<String> = emptyList()
)
