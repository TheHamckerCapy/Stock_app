package com.example.stock_app.data.csv

import com.example.stock_app.data.mappers.toIntradayInfo
import com.example.stock_app.data.remote.dto.CompanyIntradayDto
import com.example.stock_app.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntraDayInfoParser @Inject constructor() : CSVParser<IntradayInfo>{
    override suspend fun parser(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull {line->
                    val timestamp = line.getOrNull(0)?: return@mapNotNull null
                    val open = line.getOrNull(1)?: return@mapNotNull null
                    val high = line.getOrNull(2)?: return@mapNotNull null
                    val low = line.getOrNull(3)?: return@mapNotNull null
                    val close = line.getOrNull(4)?: return@mapNotNull null

                    val dto = CompanyIntradayDto(
                        timestamp,
                        open = open.toDouble(),
                        high = high.toDouble(),
                        low=low.toDouble(),
                        close = close.toDouble()

                    )
                    dto.toIntradayInfo()



                }
                .filter {
                    it.date.dayOfMonth==LocalDate.now().minusDays(2).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}