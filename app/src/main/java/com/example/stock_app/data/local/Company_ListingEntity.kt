package com.example.stock_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Company_ListingEntity(
    val symbol: String,
    val name : String,
    val exchange : String,

    @PrimaryKey(autoGenerate = true)
    val id: Int?= null
)
