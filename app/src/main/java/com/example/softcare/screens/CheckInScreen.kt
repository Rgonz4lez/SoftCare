package com.example.softcare.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CheckInScreen(navController: NavController) {
    var selectedMood by remember { mutableStateOf<String?>(null) }
    val moods = listOf(
        "😀 Feliz",
        "😐 Neutro",
        "😟 Ansioso",
        "😞 Triste",
        "😡 Estressado"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Como você está se sentindo hoje?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        moods.forEach { mood ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedMood = mood },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedMood == mood) Color(0xFFE1BEE7) else Color.White
                )
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Text(text = mood, fontSize = 18.sp)
                }
            }
        }

        selectedMood?.let {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Você escolheu: $it",
                color = Color(0xFF6A1B9A),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // redirecionar para próxima tela após resposta
                    navController.navigate("tips")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A004A))
            ) {
                Text("Continuar", color = Color.White)
            }
        }
    }
}
