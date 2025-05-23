import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.softcare.viewModel.AssessmentModel

@Composable
fun StatisticsScreen(
    navController: NavHostController,
    viewModel: AssessmentModel
) {
    var frequencies by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }

    LaunchedEffect(Unit) {
        viewModel.fetchAssessments(
            onResult = { result -> frequencies = result },
            onError = { println("Erro ao buscar dados: ${it.localizedMessage}") }
        )
    }

    val total = frequencies.values.sum().takeIf { it > 0 } ?: 1

    val allValues = frequencies.flatMap { (value, count) -> List(count) { value } }
    val average = if (allValues.isNotEmpty()) allValues.average() else 0.0

    val statusMessage = when {
        average >= 4.0 -> "😊 Você está com ótimo bem-estar emocional!"
        average >= 3.0 -> "😐 Seu bem-estar está estável. Fique atento(a)."
        else -> "⚠️ Seu bem-estar emocional pode estar em risco."
    }

    val colors = listOf(
        Color(0xFF4CAF50), // verde
        Color(0xFFFFC107), // amarelo
        Color(0xFFFF9800), // laranja
        Color(0xFFF44336), // vermelho
        Color(0xFF9C27B0)  // roxo
    )

    val labels = listOf(
        "1. Discordo totalmente",
        "2. Discordo",
        "3. Neutro",
        "4. Concordo",
        "5. Concordo totalmente"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Distribuição das Respostas",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (total > 0) {
            Canvas(modifier = Modifier.size(240.dp)) {
                var startAngle = -90f
                (1..5).forEach { value ->
                    val freq = frequencies[value] ?: 0
                    if (freq > 0) {
                        val percent = freq.toFloat() / total
                        val sweep = if (percent * 360f < 1f) 1f else percent * 360f

                        drawArc(
                            color = colors.getOrElse(value - 1) { Color.Gray },
                            startAngle = startAngle,
                            sweepAngle = sweep,
                            useCenter = true
                        )
                        startAngle += sweep
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            (1..5).forEach { value ->
                val percent = ((frequencies[value] ?: 0) * 100 / total)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                color = colors.getOrElse(value - 1) { Color.Gray },
                                shape = MaterialTheme.shapes.small
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${labels[value - 1]} — $percent%",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = statusMessage,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}