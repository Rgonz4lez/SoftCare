import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.softcare.R
import com.example.softcare.components.TipCard

data class Tip(val title: String, val content: String, val imageRes: Int)

@Composable
fun TipsScreen(navController: NavController) {
    val tips = listOf(
        Tip(
            title = "Respire fundo",
            content = "Pausas curtas com respiração consciente ajudam a reduzir o estresse.",
            imageRes = R.drawable.respiracao
        ),
        Tip(
            title = "Movimente-se",
            content = "Alongamentos ou pequenas caminhadas podem aliviar a tensão do corpo.",
            imageRes = R.drawable.andando
        ),
        Tip(
            title = "Fale com alguém",
            content = "Conversar com um colega ou escrever ajuda a processar emoções.",
            imageRes = R.drawable.falando
        ),
        Tip(
            title = "Desconecte um pouco",
            content = "Pausas sem celular ajudam a reduzir a sobrecarga mental.",
            imageRes = R.drawable.desconectar
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(tips.size) { index ->
                val tip = tips[index]
                TipCard(title = tip.title, content = tip.content, imageRes = tip.imageRes)
            }
        }
    }
}


