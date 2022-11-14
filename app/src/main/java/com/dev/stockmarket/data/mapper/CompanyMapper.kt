package com.dev.stockmarket.data.mapper

import com.dev.stockmarket.data.local.dto.CompanyListingEntity
import com.dev.stockmarket.data.remote.dto.CompanyInfoDto
import com.dev.stockmarket.domain.model.CompanyInfo
import com.dev.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing =
    CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity =
    CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo =
    CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )