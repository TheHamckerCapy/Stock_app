package com.example.stock_app.data.mappers

import com.example.stock_app.data.local.Company_ListingEntity
import com.example.stock_app.data.remote.dto.CompanyInfoDto
import com.example.stock_app.domain.model.CompanyInfo
import com.example.stock_app.domain.model.CompanyListings

fun Company_ListingEntity.toCompanyListing(): CompanyListings{
    return CompanyListings(
        symbol=symbol,
        name=name,
        exchange=exchange
    )
}

fun CompanyListings.toCompanyListingEntity(): Company_ListingEntity {
    return Company_ListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}


fun CompanyInfoDto.toCompanyInfo(): CompanyInfo{
    return CompanyInfo(
        name = name ?: "",
        symbol = symbol?: "",
        description = description?: "",
        country = country?: "",
        industry = industry?: "",
        sector = sector?: "",
        exchange = exchange?: "",
        weekLow = weekLow?: "",
        weekHigh = weekHigh?: "",
        marketCap = marketCap?: "",
        peRatio=peRatio?: ""

    )
}