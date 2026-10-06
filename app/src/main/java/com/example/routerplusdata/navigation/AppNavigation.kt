package com.example.routerplusdata.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.routerplusdata.ui.DetailsScreen
import com.example.routerplusdata.ui.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    onNavigateToDetails = { animeId ->
                        navController.navigate("details/$animeId")
                    }
                )
            }
            composable("details/{animeId}") { backStackEntry ->
                DetailsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    animeId = backStackEntry.arguments?.getString("animeId")
                )
            }
        }
    }
}