package com.example.stock_app.domain.model



data class Topper(
    val ticker: String,
    val price: String,
    val changePercentage: String
)

data class TopMovers(
    val topGainers: List<Topper>,
    val topLosers: List<Topper>,
    val mostTraded: List<Topper>
)