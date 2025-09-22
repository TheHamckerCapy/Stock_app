package com.example.stock_app.data.mappers

import com.example.stock_app.data.remote.dto.CompanyIntradayDto
import com.example.stock_app.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun CompanyIntradayDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntradayInfo(
        date = localDateTime,
        close = close,
        open = open,
        low = low,
        high = high
    )
}