package com.example.softcare.navigation

import StatisticsScreen
import TipsScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.softcare.components.BottomNavigationBar
import com.example.softcare.screens.CheckInScreen
import com.example.softcare.screens.RiskAssessmentScreen
import com.example.softcare.screens.SignInScreen
import com.example.softcare.screens.SupportScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "signIn") {
        composable("signIn") { SignInScreen(navController) }
        composable("checkin") { CheckInScreen(navController) }
        composable("tips") {
            // A partir da tela de dicas, mostra a BottomNavigationBar
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    TipsScreen(navController)
                }
                BottomNavigationBar(navController)
            }
        }
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