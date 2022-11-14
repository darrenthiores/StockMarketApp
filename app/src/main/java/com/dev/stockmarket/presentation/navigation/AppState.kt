package com.dev.stockmarket.presentation.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class AppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController
)

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController()
): AppState = remember(scaffoldState, navController) {
    AppState(
        scaffoldState,
        navController
    )
}