package com.dev.stockmarket.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.dev.stockmarket.data.mapper.toIntraDayInfo
import com.dev.stockmarket.data.remote.dto.IntraDayInfoDto
import com.dev.stockmarket.domain.model.IntraDayInfo
import com.dev.stockmarket.util.dispatchers.DispatchersProvider
import com.opencsv.CSVReader
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@RequiresApi(Build.VERSION_CODES.O)
class IntraDayInfoParser @Inject constructor(
    private val dispatcher: DispatchersProvider
) : CSVParser<IntraDayInfo> {
    override suspend fun parse(stream: InputStream): List<IntraDayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))

        return withContext(dispatcher.io) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val open = line.getOrNull(1) ?: return@mapNotNull null
                    val high = line.getOrNull(2) ?: return@mapNotNull null
                    val low = line.getOrNull(3) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null

                    val dto = IntraDayInfoDto(
                        timestamp = timestamp,
                        open = open.toDouble(),
                        high = high.toDouble(),
                        low = low.toDouble(),
                        close = close.toDouble()
                    )

                    dto.toIntraDayInfo()
                }
                .filter {
                    val minDay: Long = when(LocalDateTime.now().dayOfMonth) {
                        Calendar.SUNDAY -> 2
                        else -> 1
                    }
                    it.date.dayOfMonth == LocalDate.now().minusDays(3).dayOfMonth
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