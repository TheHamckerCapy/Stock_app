package com.example.stock_app.data.remote.dto


data class CompanyIntradayDto(
    val timestamp: String,
    val open: Double,
    val close: Double,
    val low: Double,
    val high: Double
)
