package com.example.bonialbrochures.data.entity

import com.google.gson.annotations.SerializedName

data class Embedded(
    @SerializedName("contents")
    val contentsList: List<Contents> = emptyList(),
)
