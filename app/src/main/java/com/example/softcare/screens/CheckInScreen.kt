package com.example.softcare.screens

import CheckInModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CheckInScreen(navController: NavController, viewModel: CheckInModel) {
    val selectedMood = viewModel.selectedMood
    val moods = listOf("😀 Feliz", "😐 Neutro", "😟 Ansioso", "😞 Triste", "😡 Estressado")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Como você está se sentindo hoje?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            moods.forEach { mood ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.selectedMood.value = mood },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedMood.value == mood)
                            MaterialTheme.colorScheme.secondaryContainer
                        else
                            MaterialTheme.colorScheme.surface
                    )
                ) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = mood,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            selectedMood.value?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Você escolheu: $it",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.saveCheckIn(
                            onSuccess = {
                                navController.navigate("tips") {
                                    popUpTo("checkin") { inclusive = true }
                                    launchSingleTop = true
                                }
                            },
                            onFailure = { e ->
                                println("Erro ao salvar check-in: ${e.localizedMessage}")
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Continuar", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}