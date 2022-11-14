package com.dev.stockmarket.domain.useCase

import com.dev.stockmarket.domain.model.IntraDayInfo
import com.dev.stockmarket.domain.repository.StockRepository
import com.dev.stockmarket.util.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetIntraDayInfoUseCase @Inject constructor(
    private val repository: StockRepository
) {
    suspend operator fun invoke(
        symbol: String
    ) : Resource<List<IntraDayInfo>> =
        repository.getIntraDayInfo(symbol = symbol)
}