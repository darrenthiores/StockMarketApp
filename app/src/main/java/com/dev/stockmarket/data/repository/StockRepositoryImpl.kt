package com.dev.stockmarket.data.repository

import com.dev.stockmarket.data.csv.CSVParser
import com.dev.stockmarket.data.local.source.LocalDataSource
import com.dev.stockmarket.data.mapper.toCompanyInfo
import com.dev.stockmarket.data.mapper.toCompanyListing
import com.dev.stockmarket.data.mapper.toCompanyListingEntity
import com.dev.stockmarket.data.remote.source.ApiResponse
import com.dev.stockmarket.data.remote.source.RemoteDataSource
import com.dev.stockmarket.domain.model.CompanyInfo
import com.dev.stockmarket.domain.model.CompanyListing
import com.dev.stockmarket.domain.model.IntraDayInfo
import com.dev.stockmarket.domain.repository.StockRepository
import com.dev.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intraDayInfoParser: CSVParser<IntraDayInfo>,
): StockRepository {

    override fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> = flow {
        emit(Resource.Loading())

        val localListing = localDataSource.searchCompanyListing(query)
        emit(Resource.Success(
            data = localListing.map { it.toCompanyListing() }
        ))

        val isEmpty = localListing.isEmpty() && query.isBlank()
        val shouldLoadFromCache = !isEmpty && !fetchFromRemote

        if(shouldLoadFromCache) {
            return@flow
        }

        emit(Resource.Loading())

        val remoteListing = when(val response = remoteDataSource.getCompanyListing()) {
            is ApiResponse.Empty -> {
                emit(Resource.Error("Unexpected Error Happen!"))
                null
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
                null
            }
            is ApiResponse.Success -> {
                companyListingParser.parse(response.data.byteStream())
            }
        }

        remoteListing?.let { listing ->
            localDataSource.clearCompanyListing()
            localDataSource.insertCompanyListing(
                listing.map { it.toCompanyListingEntity() }
            )

            emit(Resource.Success(
                data = localDataSource
                    .searchCompanyListing(query)
                    .map {
                        it.toCompanyListing()
                    }
            ))
        }
    }

    override suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>> =
        when(
            val response = remoteDataSource.getIntraDayInfo(symbol = symbol)
        ) {
            ApiResponse.Empty -> Resource.Error("Unexpected Error Happen!")
            is ApiResponse.Error -> Resource.Error(response.errorMessage)
            is ApiResponse.Success -> {
                val result = intraDayInfoParser.parse(response.data.byteStream())
                Resource.Success(result)
            }
        }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> =
        when(
            val response = remoteDataSource.getCompanyInfo(symbol = symbol)
        ) {
            ApiResponse.Empty -> Resource.Error("Unexpected Error Happen!")
            is ApiResponse.Error -> Resource.Error(response.errorMessage)
            is ApiResponse.Success -> Resource.Success(response.data.toCompanyInfo())
        }
}