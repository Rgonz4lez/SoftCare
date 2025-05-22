import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.softcare.components.BottomNavigationBar
import com.example.softcare.screens.CheckInScreen
import com.example.softcare.screens.RiskAssessmentScreen
import com.example.softcare.screens.SignInScreen
import com.example.softcare.screens.SupportScreen
import com.example.softcare.screens.TipsScreen
import com.example.softcare.viewModel.AssessmentModel

@Composable
fun NavGraph(navController: NavHostController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    val bottomBarScreens = listOf("checkin", "tips", "support", "assessment", "stats")

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarScreens) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "signin",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("signin") { SignInScreen(navController) }
            composable("checkin_intro") { CheckInIntroScreen(navController) }
            composable("checkin") {
                val viewModel: CheckInModel = viewModel()
                CheckInScreen(navController, viewModel)
            }
            composable("tips") { TipsScreen(navController) }
            composable("support") { SupportScreen(navController) }
            composable("assessment") {
                val viewModel: AssessmentModel = viewModel()
                RiskAssessmentScreen(navController, viewModel)
            }
            composable("stats") { StatisticsScreen(navController) }
        }
    }
}