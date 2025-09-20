package com.example.stock_app.presentation.company_listings

import com.example.stock_app.domain.model.CompanyListings

data class CompanyListingsState(
    val isLoading: Boolean = false,
    val companies: List<CompanyListings> = emptyList(),
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
