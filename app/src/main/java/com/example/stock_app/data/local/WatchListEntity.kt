package com.example.stock_app.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(tableName = "watchlists")
data class WatchListEntity(
    @PrimaryKey(autoGenerate = true)
    val watchListId: Int = 0,
    val name: String
)

@Entity(primaryKeys = ["watchListId", "symbol"])
data class WatchlistStockCrossRef(
    val watchListId: Int,
    val symbol: String
)

data class WatchlistWithStocks(
    @Embedded val watchListId: WatchListEntity,
    @Relation(
        parentColumn = "watchListId",
        entityColumn = "symbol",
        associateBy = Junction(WatchlistStockCrossRef::class)
    )
    val stocks: List<Company_ListingEntity>
)