package com.dev.stockmarket.di

import com.dev.stockmarket.data.csv.CSVParser
import com.dev.stockmarket.data.csv.CompanyListingParser
import com.dev.stockmarket.data.csv.IntraDayInfoParser
import com.dev.stockmarket.domain.model.CompanyListing
import com.dev.stockmarket.domain.model.IntraDayInfo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CsvParserModule {

    @Binds
    abstract fun provideCompanyListingParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    abstract fun provideIntraDayInfoParser(
        intraDayInfoParser: IntraDayInfoParser
    ): CSVParser<IntraDayInfo>
}