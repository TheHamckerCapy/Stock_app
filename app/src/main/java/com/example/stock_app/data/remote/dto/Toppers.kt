package com.example.stock_app.data.remote.dto

import com.squareup.moshi.Json

data class Toppers(
    @Json(name = "ticker") val ticker: String,
    @Json(name = "price") val price: String,
    @Json(name = "change_amount") val changeAmount: String,
    @Json(name = "change_percentage") val changePercentage: String,
)
