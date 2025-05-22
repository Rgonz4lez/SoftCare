package com.softcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// import androidx.navigation.compose.rememberNavController // No longer needed here
import com.example.softcare.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // val navController = rememberNavController() // Removed
            NavGraph() // Called without navController
        }
    }
}