package com.dev.stockmarket.data.local.source

import com.dev.stockmarket.data.local.dao.StockDao
import com.dev.stockmarket.data.local.db.StockDatabase
import com.dev.stockmarket.data.local.dto.CompanyListingEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val stockDb: StockDatabase
) {
    private val stockDao = stockDb.stockDao()

   suspend fun insertCompanyListing(
       companyListingEntity: List<CompanyListingEntity>
   ) {
       stockDao.insertCompanyListing(companyListingEntity)
   }

    suspend fun clearCompanyListing() {
        stockDao.clearCompanyListing()
    }

    suspend fun searchCompanyListing(
        query: String
    ): List<CompanyListingEntity> =
        stockDao.searchCompanyListing(query)
}