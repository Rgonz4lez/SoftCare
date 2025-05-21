package com.example.softcare.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.softcare.components.BottomNavigationBar

@Composable
fun RiskAssessmentScreen(navController: NavHostController) {
    val questions = listOf(
        "1. Me sinto sobrecarregado(a) nas atividades voluntárias.",
        "2. Tenho dificuldade em lidar com o sofrimento das pessoas que atendo.",
        "3. Recebo apoio suficiente da equipe.",
        "4. Sinto que meu esforço é reconhecido.",
        "5. Tenho autonomia para realizar minhas tarefas."
    )

    val responses = remember { mutableStateListOf<Int?>(null, null, null, null, null) }
    val allAnswered = responses.all { it != null }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 64.dp)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Avaliação Psicossocial",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        questions.forEachIndexed { index, question ->
            Column {
                Text(text = question, fontWeight = FontWeight.Medium)
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    (1..5).forEach { value ->
                        OutlinedButton(
                            onClick = { responses[index] = value },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = if (responses[index] == value) Color.White else Color(0xFF4A004A),
                                containerColor = if (responses[index] == value) Color(0xFF4A004A) else Color.Transparent
                            ),
                            border = ButtonDefaults.outlinedButtonBorder,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Text("$value")
                        }
                    }
                }
            }
        }

        if (allAnswered) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Obrigado por responder! Seus dados são anônimos.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF4A004A)
            )
        }
    }
}
