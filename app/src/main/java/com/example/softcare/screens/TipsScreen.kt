package com.example.softcare.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.softcare.R
import com.example.softcare.components.TipCard
import java.util.UUID

data class Tip(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val imageRes: Int,
    var isFavorite: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsScreen(navController: NavController) {
    val context = LocalContext.current

    val tips = remember {
        mutableStateListOf(
            Tip(
                title = "Respiração Diafragmática",
                content = "Inspire profundamente pelo nariz, expandindo o abdômen, e expire lentamente pela boca. Repita por 5 minutos para acalmar o sistema nervoso.",
                imageRes = R.drawable.respiracao
            ),
            Tip(
                title = "Pausa Ativa Restauradora",
                content = "A cada hora, levante-se, alongue o corpo suavemente ou caminhe por 5 minutos. Isso melhora a circulação e reduz a fadiga muscular e mental.",
                imageRes = R.drawable.andando
            ),
            Tip(
                title = "Técnica de Grounding 5-4-3-2-1",
                content = "Em momentos de ansiedade, identifique: 5 coisas que você pode ver, 4 que pode tocar, 3 que pode ouvir, 2 que pode cheirar e 1 que pode provar. Conecta você ao presente.",
                imageRes = R.drawable.respiracao
            ),
            Tip(
                title = "Diário de Gratidão",
                content = "Ao final do dia, escreva 3 coisas pelas quais você é grato(a). Focar no positivo melhora o humor e a perspectiva de vida.",
                imageRes = R.drawable.andando
            ),
            Tip(
                title = "Higiene do Sono",
                content = "Crie um ritual relaxante antes de dormir: evite telas, tome um chá calmante, leia um livro. Um sono de qualidade é vital para a saúde mental e física.",
                imageRes = R.drawable.andando
            ),
            Tip(
                title = "Conexão Social Significativa",
                content = "Reserve tempo para interações de qualidade com amigos, família ou colegas. O apoio social fortalece a resiliência emocional.",
                imageRes = R.drawable.falando
            ),
            Tip(
                title = "Mindful Eating",
                content = "Preste atenção plena às cores, texturas, aromas e sabores dos alimentos durante as refeições. Melhora a digestão e a relação com a comida.",
                imageRes = R.drawable.respiracao
            ),
            Tip(
                title = "Limite o Consumo de Notícias",
                content = "Mantenha-se informado, mas evite o excesso de notícias negativas, que podem aumentar a ansiedade. Defina horários específicos para se atualizar.",
                imageRes = R.drawable.desconectar
            )
        )
    }

    val shareTip = { title: String, content: String ->
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Dica SoftCare: $title\n\n$content")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dicas de Bem-Estar", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(tips) { index, tip ->
                    TipCard(
                        title = tip.title,
                        content = tip.content,
                        imageRes = tip.imageRes,
                        isFavorite = tip.isFavorite,
                        onFavoriteClick = {
                            tips[index] = tip.copy(isFavorite = !tip.isFavorite)
                        },
                        onShareClick = {
                            shareTip(tip.title, tip.content)
                        }
                    )
                }
            }
        }
    }
}


