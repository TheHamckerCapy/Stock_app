package com.example.stock_app.presentation.company_info

import com.example.stock_app.data.local.WatchListEntity

sealed class CompanyInfoEvent{

    object ShowWatchListSheet: CompanyInfoEvent()
    object DismissWatchListSheet: CompanyInfoEvent()
    data class AddToWatchList(val watchlist: WatchListEntity): CompanyInfoEvent()

}