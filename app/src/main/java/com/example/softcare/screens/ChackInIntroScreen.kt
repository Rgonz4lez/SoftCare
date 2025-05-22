import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CheckInIntroScreen(navController: NavController) {
    val viewModel = androidx.lifecycle.viewmodel.compose.viewModel<CheckInModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bem-vindo(a)!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4A004A)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Gostaria de fazer um check-in emocional para acompanhar como você está se sentindo hoje?",
            fontSize = 16.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate("checkin")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A004A))
        ) {
            Text("Sim, quero fazer", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Pular",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.clickable {
                viewModel.forceCheckInComplete()
                navController.navigate("tips") {
                    popUpTo("checkin_intro") { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }
}