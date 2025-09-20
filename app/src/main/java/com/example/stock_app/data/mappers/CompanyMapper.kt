package com.example.stock_app.data.mappers

import com.example.stock_app.data.local.Company_ListingEntity
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
