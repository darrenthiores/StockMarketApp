package com.dev.stockmarket.presentation.companyList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CompanyListingScreen(
    viewModel: CompanyListingViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(CompanyListingEvent.OnSearchQueryChange(it))
            },
            placeholder = {
                Text(text = "Search..")
            },
            singleLine = true
        )

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(CompanyListingEvent.Refresh) }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(
                    items = state.companies,
                    key = { index, company -> company.name + index }
                ) { index, company ->
                    CompanyItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemClick(company.symbol)
                            }
                            .padding(16.dp),
                        company = company
                    )

                    if(index < state.companies.size) {
                        Divider(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}