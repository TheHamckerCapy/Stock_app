package com.example.stock_app.data.mappers

import com.example.stock_app.data.remote.dto.Toppers
import com.example.stock_app.data.remote.dto.ToppersDto
import com.example.stock_app.domain.model.TopMovers
import com.example.stock_app.domain.model.Topper


fun Toppers.toTopper(): Topper{
    return Topper(
        ticker =ticker ,
        price = price,
        changePercentage = if (changePercentage.startsWith("-")) changePercentage else "+$changePercentage"
    )
}

fun ToppersDto.toTopMovers(): TopMovers{
    return TopMovers(
        topGainers = topGainers.map { it.toTopper() },
        topLosers = topLosers.map { it.toTopper() },
        mostTraded = mostTraded.map { it.toTopper() }
    )
}
