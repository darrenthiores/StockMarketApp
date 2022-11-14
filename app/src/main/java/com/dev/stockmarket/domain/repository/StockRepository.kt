package com.dev.stockmarket.domain.repository

import com.dev.stockmarket.domain.model.CompanyInfo
import com.dev.stockmarket.domain.model.CompanyListing
import com.dev.stockmarket.domain.model.IntraDayInfo
import com.dev.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntraDayInfo(
        symbol: String
    ): Resource<List<IntraDayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}