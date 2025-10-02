package com.example.stock_app.presentation.WatchList

import com.example.stock_app.data.local.Company_ListingEntity
import com.example.stock_app.data.local.WatchListEntity

sealed class WatchListEvents{
    data class CreateWatchList(val name: String): WatchListEvents()
    data class DeleteStock(val stock: Company_ListingEntity, val watchList: WatchListEntity): WatchListEvents()
    object ShowAddWatchlistDialog : WatchListEvents()
    object DismissAddWatchlistDialog : WatchListEvents()
    data class OnNewWatchlistNameChange(val name: String) : WatchListEvents()
    data class ToggleWatchList(val watchlist: Int): WatchListEvents()
    data class WatchListsDelete(val watchList: WatchListEntity): WatchListEvents()
}