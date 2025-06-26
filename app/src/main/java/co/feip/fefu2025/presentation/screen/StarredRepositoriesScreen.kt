package co.feip.fefu2025.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.feip.fefu2025.presentation.UiState
import co.feip.fefu2025.presentation.component.RepositoryCard
import co.feip.fefu2025.presentation.screen_repository.RepositoryListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StarredRepositoriesScreen(
    viewModel: RepositoryListViewModel,
    navController: NavController
) {
    val starredState by viewModel.starred.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Starred Repositories") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (val state = starredState) {
                is UiState.Loading -> CircularProgressIndicator()
                is UiState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.message, style = MaterialTheme.typography.bodyLarge)
                        Spacer(Modifier.height(16.dp))
                       
                        Button(onClick = { viewModel.fetchStarredRepositories() }) {
                            Text("Повторить")
                        }
                    }
                }
                is UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(state.data) { repo ->
                            Row(modifier = Modifier.clickable { navController.navigate("detail/${repo.username.trim()}") }) {
                                RepositoryCard(
                                    repository = repo,
                                    modifier = Modifier.fillMaxWidth().width(IntrinsicSize.Max)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}