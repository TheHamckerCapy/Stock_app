package com.example.stock_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Company_ListingEntity::class], version = 1)
abstract class StockDatabase: RoomDatabase() {
        abstract val dao: StockDao
}