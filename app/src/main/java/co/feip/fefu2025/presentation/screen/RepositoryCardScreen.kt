package co.feip.fefu2025.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.feip.fefu2025.presentation.UiState
import co.feip.fefu2025.presentation.screen_repository.RepositoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryCardScreen(viewModel: RepositoryViewModel, navController: NavController) {
    val state by viewModel.repositoryCard.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = when (val s = state) {
                        is UiState.Success -> s.data.username
                        else -> "Загрузка..."
                    }
                    Text(text = title, fontSize = 20.sp)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val s = state) {
                is UiState.Loading -> CircularProgressIndicator()
                is UiState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = s.message, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.fetchRepositoryCard() }) {
                            Text("Повторить")
                        }
                    }
                }
                is UiState.Success -> {
                    RepositoryCardContent(s.data)
                }
            }
        }
    }
}