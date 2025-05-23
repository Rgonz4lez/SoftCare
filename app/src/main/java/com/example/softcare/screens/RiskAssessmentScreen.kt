package com.example.softcare.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.style.TextAlign
import com.example.softcare.viewModel.AssessmentModel

@Composable
fun RiskAssessmentScreen(
    navController: NavHostController,
    viewModel: AssessmentModel
) {
    val questions = listOf(
        "1. Sinto que tenho uma carga de trabalho equilibrada nas minhas atividades.",
        "2. Consigo lidar bem com as emoções das pessoas que atendo.",
        "3. Me sinto apoiado(a) pela equipe com quem trabalho.",
        "4. Meu esforço é reconhecido pelas pessoas ao meu redor.",
        "5. Tenho autonomia e liberdade para realizar minhas tarefas."
    )

    val scrollState = rememberScrollState()
    val responses = viewModel.responses

    var confirmed by rememberSaveable { mutableStateOf(false) }
    val allAnswered = responses.size == questions.size && responses.all { it != null }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Avaliação Psicossocial",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Essa avaliação nos ajuda a entender como você está se sentindo em relação ao seu ambiente de trabalho ou voluntariado.\n\nResponda de 1 (discordo totalmente) a 5 (concordo totalmente).",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        questions.forEachIndexed { index, question ->
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = question,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 320.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).forEach { value ->
                        OutlinedButton(
                            onClick = { viewModel.saveResponse(index, value) },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = if (responses.getOrNull(index) == value)
                                    Color.White else MaterialTheme.colorScheme.primary,
                                containerColor = if (responses.getOrNull(index) == value)
                                    MaterialTheme.colorScheme.primary else Color.Transparent
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

        if (allAnswered && !confirmed) {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                if (allAnswered && !confirmed) {
                    Button(
                        onClick = {
                            viewModel.sendAssessmentToFirebase(
                                onSuccess = { confirmed = true },
                                onFailure = { e -> println("Erro ao salvar: ${e.localizedMessage}") }
                            )
                        }
                    ) {
                        Text("Confirmar Avaliação")
                    }
                }
            }
        }

        if (confirmed) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Avaliação confirmada com sucesso!\nObrigado por compartilhar como você se sente.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}