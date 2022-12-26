package com.example.bonialbrochures

import com.example.bonialbrochures.data.local.model.BrochureEntity

data class BrochureListState(
    val brochureList: List<BrochureEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)

