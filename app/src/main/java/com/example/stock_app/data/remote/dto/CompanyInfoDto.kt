package com.example.stock_app.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class CompanyInfoDto(

  @Json(name = "Name") val name: String?,
  @Json(name = "Symbol")val symbol: String?,
  @Json(name = "Description")val description: String?,
  @Json(name = "Country")val country: String?,
  @Json(name = "Industry") val industry: String?,
  @Json(name = "Sector")val sector: String?,
)