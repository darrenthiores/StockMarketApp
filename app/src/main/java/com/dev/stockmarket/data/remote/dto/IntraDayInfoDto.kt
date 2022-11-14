package com.dev.stockmarket.data.remote.dto

data class IntraDayInfoDto(
    val timestamp: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double
)
