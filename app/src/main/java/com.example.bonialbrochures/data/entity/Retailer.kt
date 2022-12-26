package com.example.bonialbrochures.data.entity

import com.google.gson.annotations.SerializedName

data class Retailer(
    @SerializedName("id")
    val retailerId: Int,
    @SerializedName("name")
    val retailerName: String
)
