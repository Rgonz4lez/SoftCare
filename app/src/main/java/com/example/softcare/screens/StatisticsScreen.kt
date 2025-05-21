import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryOf
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

@Composable
fun StatisticsScreen(navController: NavController) {
    val chartData = ChartEntryModelProducer(
        entriesOf(
            1 to 2,
            2 to 3,
            3 to 5,
            4 to 4
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Evolução Emocional",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Chart(
            chart = lineChart(),
            chartModelProducer = chartData,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Os dados apresentados refletem suas últimas interações anônimas no aplicativo.",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}