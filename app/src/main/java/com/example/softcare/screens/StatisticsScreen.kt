import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.shape.shader.verticalGradient
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.entry.entryOf
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.axis.Axis
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.Shapes.roundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun StatisticsScreen(navController: NavController) {
    val emotionalData = listOf(
        Pair("Jan", 2f),
        Pair("Fev", 3f),
        Pair("Mar", 5f),
        Pair("Abr", 4f),
        Pair("Mai", 6f)
    )
    val emotionalLabels = emotionalData.map { it.first }
    val emotionalValues = emotionalData.map { it.second }
    val lineChartData = ChartEntryModelProducer(
        emotionalValues.mapIndexed { idx, v -> entryOf(idx.toFloat(), v) }
    )

    val stressData = listOf(
        Pair("Seg", 2f),
        Pair("Ter", 3f),
        Pair("Qua", 5f),
        Pair("Qui", 4f),
        Pair("Sex", 1f)
    )
    val emotes = stressData.map { (_, value) ->
        when {
            value <= 2f -> "😀"
            value == 3f -> "😐"
            else -> "😞"
        }
    }
    val labels = stressData.map { it.first }
    val values = stressData.map { it.second }
    val columnChartData = ChartEntryModelProducer(
        values.mapIndexed { idx, v -> entryOf(idx.toFloat(), v) }
    )

    val wellnessData = listOf(
        Pair("Jan", 4f),
        Pair("Fev", 5f),
        Pair("Mar", 6f),
        Pair("Abr", 5f),
        Pair("Mai", 2f)
    )
    val wellnessLabels = wellnessData.map { it.first }
    val wellnessValues = wellnessData.map { it.second }
    val wellnessChartData = ChartEntryModelProducer(
        wellnessValues.mapIndexed { idx, v -> entryOf(idx.toFloat(), v) }
    )
    val wellnessAverage = if (wellnessValues.isNotEmpty()) wellnessValues.average() else 0.0
    val wellnessCurrent = wellnessValues.lastOrNull() ?: 0f
    val showWellnessWarning = wellnessCurrent < wellnessAverage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Suas Estatísticas",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Evolução Emocional",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                androidx.compose.animation.AnimatedVisibility(
                    visible = true,
                    enter = androidx.compose.animation.expandVertically() + androidx.compose.animation.fadeIn()
                ) {
                    Chart(
                        chart = lineChart(
                            lines = listOf(
                                LineChart.LineSpec(
                                    lineColor = MaterialTheme.colorScheme.primary.toArgb(),
                                    lineBackgroundShader = verticalGradient(
                                        arrayOf(
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0f)
                                        )
                                    )
                                )
                            )
                        ),
                        chartModelProducer = lineChartData,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    emotionalLabels.forEach { label ->
                        Text(label, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Nível de Estresse",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    emotes.forEach { emote ->
                        Text(emote, fontSize = 22.sp)
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = true,
                    enter = androidx.compose.animation.expandVertically() + androidx.compose.animation.fadeIn()
                ) {
                    Chart(
                        chart = columnChart(
                            listOf(
                                LineComponent(
                                    color = MaterialTheme.colorScheme.secondary.toArgb(),
                                    thicknessDp = 28f,
                                    shape = Shapes.roundedCornerShape(12)
                                )
                            )
                        ),
                        chartModelProducer = columnChartData,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    labels.forEach { label ->
                        Text(label, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Nível de Bem-Estar",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                androidx.compose.animation.AnimatedVisibility(
                    visible = true,
                    enter = androidx.compose.animation.expandVertically() + androidx.compose.animation.fadeIn()
                ) {
                    Chart(
                        chart = lineChart(
                            lines = listOf(
                                LineChart.LineSpec(
                                    lineColor = MaterialTheme.colorScheme.primary.toArgb(),
                                    lineBackgroundShader = verticalGradient(
                                        arrayOf(
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0f)
                                        )
                                    )
                                )
                            )
                        ),
                        chartModelProducer = wellnessChartData,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    wellnessLabels.forEach { label ->
                        Text(label, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
                if (showWellnessWarning) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.error.copy(alpha = 0.08f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.error), shape = RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Você pode estar enfrentando alguns desafios que impactam seu bem estar, converse sobre seus resultados no nosso Canal de Escuta",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Resumo da Semana",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatisticItem(
                        label = "Check-ins",
                        value = "12",
                        color = MaterialTheme.colorScheme.primary
                    )
                    StatisticItem(
                        label = "Média",
                        value = "4.2",
                        color = MaterialTheme.colorScheme.secondary
                    )
                    StatisticItem(
                        label = "Melhor Dia",
                        value = "Sexta",
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}

@Composable
fun StatisticItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            color = color,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}