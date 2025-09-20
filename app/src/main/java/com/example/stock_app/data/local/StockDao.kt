package com.example.stock_app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface StockDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntities: List<Company_ListingEntity>
    )

    @Query("DELETE FROM company_listingentity")
    suspend fun clearCompanyListings()

    @Query(
        """
            SELECT * FROM 
            company_listingentity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query) == symbol
            """
    )
    suspend fun searchCompanyListings(query: String ): List<Company_ListingEntity>

}