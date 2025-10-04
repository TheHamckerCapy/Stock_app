package com.example.stock_app.presentation.BullsBearScreen

import com.example.stock_app.domain.model.Topper


data class ToppersState(
    val topGainers: List<Topper> = emptyList(),
    val topLosers: List<Topper> = emptyList(),
    val mostTraded: List<Topper> = emptyList(),
    val isLoading: Boolean =false,
    val error: String? = null,
    val stocks: List<Topper> = emptyList(),
    val title: String = ""
)