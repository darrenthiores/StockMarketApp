package com.dev.stockmarket.data.csv

import com.dev.stockmarket.domain.model.CompanyListing
import com.dev.stockmarket.util.dispatchers.DispatchersProvider
import com.opencsv.CSVReader
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingParser @Inject constructor(
    private val dispatcher: DispatchersProvider
) : CSVParser<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(InputStreamReader(stream))

        return withContext(dispatcher.io) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchange = line.getOrNull(2)

                    CompanyListing(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null,
                    )
                }
                .also {
                    csvReader.close()
                }
        }
    }
}