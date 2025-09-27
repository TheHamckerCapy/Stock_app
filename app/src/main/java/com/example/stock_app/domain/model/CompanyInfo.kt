package com.example.stock_app.domain.model

data class CompanyInfo(

    val name: String,
    val symbol: String,
    val description: String,
    val country: String,
    val industry: String,
    val sector: String,
    val exchange: String,
    val weekLow: String,
    val weekHigh: String,
    val marketCap: String,
    val peRatio: String
)
