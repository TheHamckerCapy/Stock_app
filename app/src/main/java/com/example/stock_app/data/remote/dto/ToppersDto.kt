package com.example.stock_app.data.remote.dto

import com.squareup.moshi.Json

data class ToppersDto(
    @Json(name = "top_gainers")val topGainers: List<Toppers>,
    @Json(name = "top_losers")val topLosers: List<Toppers>
)
