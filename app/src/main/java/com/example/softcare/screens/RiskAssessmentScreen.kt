package com.example.softcare.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// Data class for better organization and future expansion
data class QuestionItem(
    val id: String,
    val category: String,
    val text: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiskAssessmentScreen(navController: NavHostController) {
    val context = LocalContext.current

    val questions = remember {
        listOf(
            // Carga de Trabalho e Estresse
            QuestionItem("q1", "Carga de Trabalho e Estresse", "Sinto que minhas atividades voluntárias estão em equilíbrio com meu bem-estar."),
            QuestionItem("q6", "Carga de Trabalho e Estresse", "Consigo gerenciar as demandas do meu trabalho voluntário sem me sentir excessivamente pressionado(a)."),
            QuestionItem("q7", "Carga de Trabalho e Estresse", "Tenho tempo suficiente para realizar minhas tarefas voluntárias com a qualidade que considero ideal."),

            // Apoio Social e Reconhecimento
            QuestionItem("q3", "Apoio Social e Reconhecimento", "Sinto que posso contar com a minha equipe quando preciso de apoio."),
            QuestionItem("q4", "Apoio Social e Reconhecimento", "Meu trabalho voluntário me traz uma sensação de realização e valor."),
            QuestionItem("q8", "Apoio Social e Reconhecimento", "Sinto que há um ambiente de respeito e colaboração mútua na equipe."),
            QuestionItem("q9", "Apoio Social e Reconhecimento", "Recebo feedback construtivo sobre meu desempenho voluntário de forma regular."),

            // Autonomia e Tomada de Decisão
            QuestionItem("q5", "Autonomia e Tomada de Decisão", "Tenho a liberdade e confiança necessárias para realizar minhas atividades voluntárias da melhor forma."),
            QuestionItem("q10", "Autonomia e Tomada de Decisão", "Sinto que posso influenciar as decisões que afetam diretamente meu trabalho voluntário."),

            // Propósito e Satisfação
            QuestionItem("q2", "Propósito e Satisfação", "Sinto-me preparado(a) e com recursos para lidar com as emoções que surgem ao ajudar os outros."),
            QuestionItem("q11", "Propósito e Satisfação", "Sinto que meu trabalho voluntário contribui para algo que considero genuinamente importante."),
            QuestionItem("q12", "Propósito e Satisfação", "Estou satisfeito(a) com as oportunidades de desenvolvimento e aprendizado que o voluntariado me oferece."),

            // Equilíbrio entre Vida Pessoal e Voluntariado
            QuestionItem("q13", "Equilíbrio entre Vida Pessoal e Voluntariado", "Consigo desconectar do trabalho voluntário e dedicar tempo de qualidade para minhas atividades pessoais e descanso."),
            QuestionItem("q14", "Equilíbrio entre Vida Pessoal e Voluntariado", "O voluntariado complementa minha vida de forma positiva, sem gerar conflitos excessivos com outras áreas."),
            QuestionItem("q15", "Equilíbrio entre Vida Pessoal e Voluntariado", "Sinto que os horários e a dedicação ao voluntariado são compatíveis com meus outros compromissos.")
        )
    }

    val likertLabels = listOf(
        "Discordo Totalmente",
        "Discordo",
        "Neutro",
        "Concordo",
        "Concordo Totalmente"
    )

    val responses = remember { mutableStateMapOf<String, Int>() }
    val allAnswered = responses.size == questions.size
    var submissionMessageVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Avaliação Psicossocial", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            // com.example.softcare.components.BottomNavigationBar(navController = navController)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Reduced spacing between cards
            ) {
                var currentCategory = ""
                itemsIndexed(questions) { index, question ->
                    if (question.category != currentCategory) {
                        currentCategory = question.category
                        Text(
                            text = currentCategory,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 8.dp)
                                .semantics { contentDescription = "Categoria da pergunta: $currentCategory" }
                        )
                    }
                    QuestionCard(
                        question = question,
                        questionNumber = index + 1, // Pass sequential number
                        likertLabels = likertLabels,
                        selectedResponse = responses[question.id],
                        onResponseSelected = { responseValue ->
                            responses[question.id] = responseValue
                            submissionMessageVisible = false
                        }
                    )
                }
            }

            if (submissionMessageVisible) {
                SuccessMessage()
            }

            Button(
                onClick = {
                    submissionMessageVisible = true
                },
                enabled = allAnswered,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(50.dp)
                    .semantics { contentDescription = "Enviar respostas da avaliação psicossocial" }
            ) {
                Text("Enviar Avaliação", fontSize = 16.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionCard(
    question: QuestionItem,
    questionNumber: Int, // Added questionNumber
    likertLabels: List<String>,
    selectedResponse: Int?,
    onResponseSelected: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "${questionNumber}. ${question.text}", // Display number and text
                style = MaterialTheme.typography.titleMedium, // Adjusted for consistency
                fontWeight = FontWeight.Normal, // Adjusted for better readability
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                likertLabels.forEachIndexed { index, label ->
                    val value = index + 1
                    val isSelected = selectedResponse == value

                    val backgroundColor by animateColorAsState(
                        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                        label = "Button Background Color Animation"
                    )
                    val contentColor by animateColorAsState(
                        targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                        label = "Button Content Color Animation"
                    )
                    val borderColor by animateColorAsState(
                        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        label = "Button Border Color Animation"
                    )

                    OutlinedButton(
                        onClick = { onResponseSelected(value) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = backgroundColor,
                            contentColor = contentColor
                        ),
                        border = BorderStroke(1.dp, borderColor),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 2.dp)
                            .semantics {
                                contentDescription = "Selecionar resposta para a pergunta ${questionNumber}: ${question.text}, opção $value: $label"
                            }
                    ) {
                        Text(
                            text = "$value",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            if (selectedResponse != null && selectedResponse in 1..likertLabels.size) {
                Text(
                    text = "Sua seleção: ${likertLabels[selectedResponse - 1]}",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun SuccessMessage() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(
                MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = "Ícone de sucesso",
            tint = MaterialTheme.colorScheme.onTertiaryContainer
        )
        Text(
            text = "Obrigado por responder! Seus dados são anônimos e contribuem para um ambiente de trabalho mais saudável.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// Placeholder for BottomNavigationBar if it's part of your project
// @Composable
// fun BottomNavigationBar(navController: NavHostController) {
//    // Implementation
// }
