package com.example.stock_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [
        Company_ListingEntity::class,
        WatchListEntity::class,
        WatchlistStockCrossRef::class
    ], version = 2
)
abstract class StockDatabase : RoomDatabase() {
    abstract val dao: StockDao
    abstract val watchlistDao: WatchListDao
}