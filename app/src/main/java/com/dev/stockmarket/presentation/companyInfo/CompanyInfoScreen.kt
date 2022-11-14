package com.dev.stockmarket.presentation.companyInfo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CompanyInfoScreen(
    viewModel: CompanyInfoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    if(state.error == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            state.company?.let { company ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = company.name,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = company.symbol,
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontStyle = FontStyle.Italic
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Industry: ${company.industry}",
                    style = MaterialTheme.typography.subtitle2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Country: ${company.country}",
                    style = MaterialTheme.typography.subtitle2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = company.description,
                    style = MaterialTheme.typography.caption
                )

                if(state.stockIntraDays.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Market Summary"
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    StockChart(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        infos = state.stockIntraDays
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(state.isLoading) {
            CircularProgressIndicator()
        } else if(state.error != null) {
            Text(
                text = state.error ?: "",
                color = MaterialTheme.colors.error
            )
        }
    }
}