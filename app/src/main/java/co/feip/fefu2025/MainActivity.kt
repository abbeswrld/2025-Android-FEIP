package co.feip.fefu2025
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
<<<<<<< Updated upstream
import androidx.activity.enableEdgeToEdge
=======
import androidx.navigation.compose.rememberNavController
import co.feip.fefu2025.data.repository.RepositoryCardImpl
import co.feip.fefu2025.domain.usecase.GetRepositoryCardUseCase
import co.feip.fefu2025.presentation.screen.RepositoryListViewModel
>>>>>>> Stashed changes
import co.feip.fefu2025.ui.theme.FEFU2025AndroidBaseRepoTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repositoryUseCase = GetRepositoriesUseCase(RepositoryImpl())
        val repositoryCardUseCaseFactory: (String) -> GetRepositoryCardUseCase = { username ->
            GetRepositoryCardUseCase(RepositoryCardImpl(username))
        }

        val listViewModel = RepositoryListViewModel(repositoryUseCase)
        setContent {
            FEFU2025AndroidBaseRepoTheme {
<<<<<<< Updated upstream
                // Вызов LanguageDistributionBar с параметрами
                LanguageDistributionBar(
                    languages = listOf(
                        "Kotlin" to 60f,
                        "Java" to 30f,
                        "XML" to 10f
                    ),
                    colors = listOf(
                        0xFFA97BFF.toInt(), // Фиолетовый для Kotlin
                        0xFFB07219.toInt(), // Коричневый для Java
                        0xFFF34B7D.toInt()  // Розовый для XML
                    )
=======
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    listViewModel = listViewModel,
                    cardViewModelFactory = { username ->
                        val cardUseCase = repositoryCardUseCaseFactory(username)
                        RepositoryViewModel(cardUseCase)
                    }
>>>>>>> Stashed changes
                )
            }
        }
    }
}