package com.example.stock_app.data.remote


import com.example.stock_app.Constant.api_key
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("/query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String= API_KEY
    ): ResponseBody

    companion object{
        val API_KEY = api_key
        const val BASE_URL = "https://www.alphavantage.co"
    }
}