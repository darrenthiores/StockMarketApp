package com.dev.stockmarket.presentation.companyList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.stockmarket.domain.useCase.GetCompanyListingUseCase
import com.dev.stockmarket.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val getCompanyListingUseCase: GetCompanyListingUseCase
): ViewModel() {

    private val _state = MutableStateFlow(CompanyListingState())
    val state: StateFlow<CompanyListingState> = _state.asStateFlow()

    private var searchJob: Job? = null

    fun onEvent(event: CompanyListingEvent) {
        when(event) {
            is CompanyListingEvent.OnSearchQueryChange -> {
                _state.value = state.value.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListing()
                }
            }
            CompanyListingEvent.Refresh -> getCompanyListing(fetchFromRemote = true)
        }
    }

    private fun getCompanyListing(
        query: String = state.value.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            getCompanyListingUseCase(
                query = query,
                fetchFromRemote = fetchFromRemote
            ).collect { result ->
                when(result) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        result.data?.let { listing ->
                            _state.value = state.value.copy(
                                companies = listing,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    init {
        getCompanyListing()
    }
}
