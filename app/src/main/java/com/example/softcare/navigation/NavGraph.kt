package com.example.softcare.navigation

import StatisticsScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
// import androidx.navigation.NavHostController // Not directly used as parameter anymore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.softcare.components.BottomNavigationBar
import com.example.softcare.screens.CheckInScreen
import com.example.softcare.screens.RiskAssessmentScreen
import com.example.softcare.screens.SignInScreen
import com.example.softcare.screens.SupportScreen
import com.example.softcare.screens.TipsScreen


@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "signIn") {
        composable("signIn") { SignInScreen(navController) }
        composable("checkin") { CheckInScreen(navController) }
        composable("tips") { TipsScreen(navController) }
        composable("assessment") {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    RiskAssessmentScreen(navController)
                }
                BottomNavigationBar(navController)
            }
        }
        composable("support") {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    SupportScreen(navController)
                }
                BottomNavigationBar(navController)
            }
        }
        composable("stats") {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    StatisticsScreen(navController)
                }
                BottomNavigationBar(navController)
            }
        }
    }
}