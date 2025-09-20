package com.example.stock_app.presentation.company_listings

sealed class CompanyListingsEvents {
    object Refresh: CompanyListingsEvents()
    data class OnSearchQueryChange(val query: String): CompanyListingsEvents()
}