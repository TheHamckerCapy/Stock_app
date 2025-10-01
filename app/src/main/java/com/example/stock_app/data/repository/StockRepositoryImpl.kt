package com.example.stock_app.data.repository

import android.util.Log
import com.example.stock_app.data.csv.CSVParser
import com.example.stock_app.data.local.StockDatabase
import com.example.stock_app.data.local.WatchListEntity
import com.example.stock_app.data.local.WatchlistStockCrossRef
import com.example.stock_app.data.local.WatchlistWithStocks
import com.example.stock_app.data.mappers.toCompanyInfo
import com.example.stock_app.data.mappers.toCompanyListing
import com.example.stock_app.data.mappers.toCompanyListingEntity
import com.example.stock_app.data.remote.StockApi
import com.example.stock_app.domain.model.CompanyInfo
import com.example.stock_app.domain.model.CompanyListings
import com.example.stock_app.domain.model.IntradayInfo
import com.example.stock_app.domain.repository.StockRepository
import com.example.stock_app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase,
    val companyListingParser: CSVParser<CompanyListings>,
    val intradatListingParser: CSVParser<IntradayInfo>
) : StockRepository {

    private val dao = db.dao
    private val watchListdao = db.watchlistDao

    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListings>>> {

        return flow {
            emit(Resource.Loading(true))
            val localListing = dao.searchCompanyListings(query)
            emit(
                Resource.Success(
                    data = localListing.map { it.toCompanyListing() }
                ))

            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                companyListingParser.parser(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldnt Load data "))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Http error"))
                null
            }

            remoteListings?.let { listings ->

                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(
                    Resource.Success(
                        data = dao.searchCompanyListings("").map { it.toCompanyListing() }
                    ))
                emit(Resource.Loading(false))


            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntraday(symbol)
            val result = intradatListingParser.parser(response.byteStream())
            Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load intraday info")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load intraday info")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val response = api.getCompanyOverview(symbol)
            Log.d("CompanyInfo", "Response: $response")
            Resource.Success(response.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load Company info")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load Company info")
        }
    }

    override fun getAllWatchList(): Flow<List<WatchListEntity>> {
       return watchListdao.getAllWatchlists()
    }

    override fun getWatchListwithStocks(id: Int): Flow<WatchlistWithStocks> {
        return watchListdao.getWatchlistWithStocks(id)
    }

    override suspend fun insertWatchList(name: String) {
        watchListdao.insertWatchList(
            watchlist = WatchListEntity(name=name)
        )
    }

    override suspend fun addStockToWatchList(symbol: String, watchlistId: Int) {
        watchListdao.addStockToWatchList(crossRef = WatchlistStockCrossRef(watchlistId,symbol))
    }

    override suspend fun deleteWatchList(symbol: String, watchlistId: Int) {
        watchListdao.deleteWatchList(crossRef = WatchlistStockCrossRef(watchlistId,symbol))
    }
    override fun getAllWatchlistsWithStocks(): Flow<List<WatchlistWithStocks>> {
        return watchListdao.getAllWatchlistsWithStocks()
    }
}