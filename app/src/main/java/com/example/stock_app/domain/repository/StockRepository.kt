package com.example.stock_app.domain.repository

import com.example.stock_app.data.local.WatchListEntity
import com.example.stock_app.data.local.WatchlistWithStocks
import com.example.stock_app.domain.model.CompanyInfo
import com.example.stock_app.domain.model.CompanyListings
import com.example.stock_app.domain.model.IntradayInfo
import com.example.stock_app.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListings>>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>

    fun getAllWatchList(): Flow<List<WatchListEntity>>

    fun getWatchListwithStocks(id:Int): Flow<WatchlistWithStocks>

    fun getAllWatchlistsWithStocks(): Flow<List<WatchlistWithStocks>>

    suspend fun insertWatchList(name: String)

    suspend fun addStockToWatchList(symbol: String, watchlistId: Int)

    suspend fun deleteWatchList(symbol: String, watchlistId: Int)

    suspend fun deleteWatchLists(watchListEntity: WatchListEntity)
}
