package com.example.softcare.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Mood(val emoji: String, val description: String, val id: String) {
    val fullText = "$emoji $description"
}

@Composable
fun MoodCard(
    mood: Mood,
    isSelected: Boolean,
    onMoodSelected: (Mood) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(durationMillis = 300)
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(durationMillis = 300)
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = tween(durationMillis = 300)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium) // Clip for ripple effect
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = MaterialTheme.colorScheme.primary),
                onClick = { onMoodSelected(mood) }
            )
            .semantics { contentDescription = "Opção de humor: ${mood.description}" },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        border = BorderStroke(2.dp, borderColor)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 24.dp) // Increased padding
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = mood.fullText,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = contentColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CheckInScreen(navController: NavController) {
    var selectedMood by remember { mutableStateOf<Mood?>(null) }
    val moods = remember {
        listOf(
            Mood("😀", "Feliz", "happy"),
            Mood("🙂", "Normal", "neutral"), // Changed from Neutro to Normal and emoji
            Mood("😟", "Ansioso", "anxious"),
            Mood("😞", "Triste", "sad"),
            Mood("😡", "Estressado", "stressed")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 32.dp), // Adjusted padding
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Olá! Como você está se sentindo agora?", // More empathetic text
            style = MaterialTheme.typography.headlineSmall, // Adjusted style
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp) // Increased bottom padding
        )

        // Using LazyColumn for potential future growth, though Column is also fine for 5 items.
        // For simplicity with current item count and animations, sticking to Column.
        // If items increase, LazyColumn with item key = mood.id is recommended.
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp) // Reduced spacing between cards
        ) {
            moods.forEach { mood ->
                MoodCard(
                    mood = mood,
                    isSelected = selectedMood == mood,
                    onMoodSelected = { selectedMood = it }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Pushes content below to the bottom

        AnimatedVisibility(visible = selectedMood != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                selectedMood?.let {
                    Text(
                        text = "Que bom que compartilhou! Você selecionou: ${it.description}", // More empathetic text
                        style = MaterialTheme.typography.titleMedium, // Adjusted style
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 20.dp) // Adjusted padding
                    )

                    Button(
                        onClick = {
                            // Navigate to sign in screen
                            navController.navigate("assessment") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        },
                        shape = RoundedCornerShape(12.dp), // More rounded corners
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp) // Increased height
                            .padding(horizontal = 16.dp), // Horizontal padding for button
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 8.dp)
                    ) {
                        Text(
                            text = "Fazer Avaliação de Bem-Estar", // More descriptive text
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
        // If no mood is selected, ensure there's some space at the bottom or a call to action
        if (selectedMood == null) {
            Spacer(modifier = Modifier.height(56.dp + 40.dp)) // Placeholder for button and text above it
        }
    }
}
