package com.dev.stockmarket.presentation.companyInfo

import com.dev.stockmarket.domain.model.CompanyInfo
import com.dev.stockmarket.domain.model.IntraDayInfo

data class CompanyInfoState(
    val stockIntraDays: List<IntraDayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)