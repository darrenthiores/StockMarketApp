package com.dev.stockmarket.domain.useCase

import com.dev.stockmarket.domain.model.CompanyListing
import com.dev.stockmarket.domain.repository.StockRepository
import com.dev.stockmarket.util.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetCompanyListingUseCase @Inject constructor(
    private val repository: StockRepository
) {
    operator fun invoke(
        fetchFromRemote: Boolean,
        query: String
    ) : Flow<Resource<List<CompanyListing>>> =
        repository.getCompanyListings(
            fetchFromRemote = fetchFromRemote,
            query = query
        )
}