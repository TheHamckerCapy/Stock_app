package com.example.stock_app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface WatchListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchList(watchlist: WatchListEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStockToWatchList(crossRef: WatchlistStockCrossRef)

    @Delete
    suspend fun deleteWatchList(crossRef: WatchlistStockCrossRef)


    @Query("SELECT * FROM watchlists")
    fun getAllWatchlists(): Flow<List<WatchListEntity>>

    @Transaction
    @Query("SELECT * FROM watchlists WHERE watchlistId = :id")
    fun getWatchlistWithStocks(id: Int): Flow<WatchlistWithStocks>

    @Transaction
    @Query("SELECT * FROM watchlists")
    fun getAllWatchlistsWithStocks(): Flow<List<WatchlistWithStocks>>

    @Delete
    suspend fun deleteWatchLists(watchlist: WatchListEntity)
}