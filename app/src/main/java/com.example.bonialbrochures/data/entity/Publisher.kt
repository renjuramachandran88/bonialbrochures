package com.example.bonialbrochures.data.entity

import com.google.gson.annotations.SerializedName

data class Publisher(
    @SerializedName("id")
    val publisherId: Long,
    @SerializedName("name")
    val publisherName: String,
    @SerializedName("type")
    val publisherType: String
)
