package com.example.stock_app.presentation.WatchList

import com.example.stock_app.data.local.WatchListEntity
import com.example.stock_app.data.local.WatchlistWithStocks

data class WatchListState (
    val watchLists: List<WatchlistWithStocks> = emptyList(),
    val isLoading: Boolean = false,
    val isAddWatchListDiaglogShown: Boolean = false,
    val newWatchListName: String = "",
    val isExpandedId: Int? = null
)