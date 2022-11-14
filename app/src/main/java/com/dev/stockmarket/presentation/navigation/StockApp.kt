package com.dev.stockmarket.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dev.stockmarket.presentation.companyInfo.CompanyInfoScreen
import com.dev.stockmarket.presentation.companyList.CompanyListingScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StockApp(
    appState: AppState = rememberAppState()
) {
    Scaffold(
        scaffoldState = appState.scaffoldState
    ) { paddingValues ->
        StockNavHost(
            modifier = Modifier.padding(paddingValues),
            navController = appState.navController
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StockNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = StockScreen.CompanyListing.name
    ) {
        composable(StockScreen.CompanyListing.name) {
            CompanyListingScreen(
                onItemClick = { symbol ->
                    navController.navigate("${StockScreen.CompanyDetail.name}/$symbol")
                }
            )
        }
        composable("${StockScreen.CompanyDetail.name}/{symbol}") {
            CompanyInfoScreen()
        }
    }
}