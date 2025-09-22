package com.example.stock_app.domain.model

import java.time.LocalDateTime

data class IntradayInfo(
    val date: LocalDateTime,
    val open: Double,
    val close: Double,
    val low : Double,
    val high: Double
)
