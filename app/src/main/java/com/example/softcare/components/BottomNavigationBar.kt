package com.example.softcare.components


import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Mood, contentDescription = "Check-in") },
            selected = currentRoute == "checkin",
            onClick = { navController.navigate("checkin") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Assessment, contentDescription = "Avaliação") },
            selected = currentRoute == "assessment",
            onClick = { navController.navigate("assessment") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.SupportAgent, contentDescription = "Escuta") },
            selected = currentRoute == "support",
            onClick = { navController.navigate("support") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.TipsAndUpdates, contentDescription = "Dicas") },
            selected = currentRoute == "tips",
            onClick = { navController.navigate("tips") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.BarChart, contentDescription = "Estatísticas") },
            selected = currentRoute == "stats",
            onClick = { navController.navigate("stats") }
        )
    }
}
