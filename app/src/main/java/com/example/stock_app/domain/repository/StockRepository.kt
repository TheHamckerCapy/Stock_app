package com.example.stock_app.domain.repository

import com.example.stock_app.domain.model.CompanyListings
import com.example.stock_app.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListings>>>
}
