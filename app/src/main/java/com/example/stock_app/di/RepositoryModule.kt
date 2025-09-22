package com.example.stock_app.di

import com.example.stock_app.data.csv.CSVParser
import com.example.stock_app.data.csv.CompanyListingParser
import com.example.stock_app.data.csv.IntraDayInfoParser
import com.example.stock_app.data.repository.StockRepositoryImpl
import com.example.stock_app.domain.model.CompanyListings
import com.example.stock_app.domain.model.IntradayInfo
import com.example.stock_app.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

        @Binds
        @Singleton
        abstract fun bindsCompanyListingParser(
            companyListingParser: CompanyListingParser
        ): CSVParser<CompanyListings>


        @Binds
        @Singleton
        abstract fun bindsIntradayInfoParser(
            intraDayInfoParser: IntraDayInfoParser
        ): CSVParser<IntradayInfo>

        @Binds
        @Singleton
        abstract fun bindsStockRepository(
            stockRepositoryImpl: StockRepositoryImpl
        ): StockRepository

}