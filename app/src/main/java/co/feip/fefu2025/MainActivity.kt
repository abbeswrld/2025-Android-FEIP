package co.feip.fefu2025
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController

import co.feip.fefu2025.data.repository.RepositoryCardImpl
import co.feip.fefu2025.data.repository.RepositoryImpl
import co.feip.fefu2025.domain.usecase.GetRepositoriesUseCase
import co.feip.fefu2025.domain.usecase.GetRepositoryCardUseCase
import co.feip.fefu2025.domain.usecase.SearchRepositoriesUseCase
import co.feip.fefu2025.navigation.AppNavHost
import co.feip.fefu2025.presentation.screen_repository.RepositoryListViewModel
import co.feip.fefu2025.presentation.screen_repository.RepositoryViewModel
import co.feip.fefu2025.ui.theme.FEFU2025AndroidBaseRepoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val repositoryImpl = RepositoryImpl()


        val getRepositoriesUseCase = GetRepositoriesUseCase(repositoryImpl)
        val searchRepositoriesUseCase = SearchRepositoriesUseCase(repositoryImpl)
        val repositoryCardUseCaseFactory: (String) -> GetRepositoryCardUseCase = { username ->
            GetRepositoryCardUseCase(RepositoryCardImpl(username))
        }

        
        val listViewModel = RepositoryListViewModel(getRepositoriesUseCase, searchRepositoriesUseCase)

        setContent {
            FEFU2025AndroidBaseRepoTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    listViewModel = listViewModel,
                    cardViewModelFactory = { username ->
                        val cardUseCase = repositoryCardUseCaseFactory(username)
                        RepositoryViewModel(cardUseCase)
                    }
                )
            }
        }
    }
}