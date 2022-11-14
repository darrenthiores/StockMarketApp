package com.dev.stockmarket.presentation.companyList

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dev.stockmarket.domain.model.CompanyListing

@Composable
fun CompanyItem(
    modifier: Modifier = Modifier,
    company: CompanyListing
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = company.name,
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onBackground
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = company.exchange,
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colors.onBackground
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "(${company.symbol})",
                style = MaterialTheme.typography.subtitle2.copy(
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colors.onBackground
                )
            )
        }
    }
}