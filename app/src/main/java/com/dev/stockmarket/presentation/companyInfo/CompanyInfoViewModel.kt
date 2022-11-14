package com.dev.stockmarket.presentation.companyInfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.stockmarket.domain.useCase.GetCompanyInfoUseCase
import com.dev.stockmarket.domain.useCase.GetIntraDayInfoUseCase
import com.dev.stockmarket.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val getCompanyInfoUseCase: GetCompanyInfoUseCase,
    private val getIntraDayInfoUseCase: GetIntraDayInfoUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(CompanyInfoState())
    val state: StateFlow<CompanyInfoState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            _state.value = state.value.copy(
                isLoading = true
            )

            val companyInfoResult = async { getCompanyInfoUseCase(symbol) }
            val intraDayInfoResult = async { getIntraDayInfoUseCase(symbol) }

            when(val result = companyInfoResult.await()) {
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        error = result.message,
                        isLoading = false,
                        company = null
                    )
                }
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        company = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                else -> Unit
            }

            when(val result = intraDayInfoResult.await()) {
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        error = result.message,
                        isLoading = false,
                        stockIntraDays = emptyList()
                    )
                }
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        stockIntraDays = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                else -> Unit
            }
        }
    }
}