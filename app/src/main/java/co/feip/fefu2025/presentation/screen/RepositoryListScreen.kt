
package co.feip.fefu2025.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.presentation.UiState
import co.feip.fefu2025.presentation.component.RepositoryCard
import co.feip.fefu2025.presentation.screen_repository.RepositoryListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryListScreen(
    viewModel: RepositoryListViewModel,
    onItemClick: (String) -> Unit,
    navController: NavHostController
) {
    val repositoriesState by viewModel.repositories.collectAsState()
    val starredState by viewModel.starred.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResultsState by viewModel.searchResults.collectAsState()
    var isSearchActive by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.onSearchQueryChange(it) },
                onSearch = { isSearchActive = false },
                active = isSearchActive,
                onActiveChange = { isSearchActive = it },
                placeholder = { Text("Поиск по репозиториям") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = if (isSearchActive) 0.dp else 16.dp)
                    .padding(top = 8.dp, bottom = 8.dp)
            ) {
                when (val state = searchResultsState) {
                    is UiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                    is UiState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(state.message)
                    }
                    is UiState.Success -> {
                        if (searchQuery.isNotBlank() && state.data.isEmpty()) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Ничего не найдено")
                            }
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(state.data) { repo ->
                        
                                    Row(modifier = Modifier.clickable { onItemClick(repo.username.trim()) }) {
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
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val state = repositoriesState) {
                is UiState.Loading -> CircularProgressIndicator()
                is UiState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(state.message, style = MaterialTheme.typography.bodyLarge)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { viewModel.fetchRepositories() }) { Text("Повторить") }
                    }
                }
                is UiState.Success -> {
                    MainContent(
                        repositories = state.data,
                        starredState = starredState,
                        onItemClick = onItemClick,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun MainContent(
    repositories: List<Repository>,
    starredState: UiState<List<Repository>>,
    onItemClick: (String) -> Unit,
    navController: NavHostController
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = "My Stars",
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    .clickable { navController.navigate("starred") }
            )

            when (val state = starredState) {
                is UiState.Loading -> Box(
                    Modifier.fillMaxWidth().height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                is UiState.Error -> Text(text = state.message, Modifier.padding(16.dp))
                is UiState.Success -> {
                    LazyRow(contentPadding = PaddingValues(horizontal = 4.dp)) {
                        items(state.data) { repo ->
                            RepositoryCard(repo, modifier = Modifier.clickable { onItemClick(repo.username.trim()) })
                        }
                    }
                }
            }
        }
        item {
            Text(
                text = "All Projects",
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
        }

        items(repositories) { repo ->
            Row(modifier = Modifier.clickable { onItemClick(repo.username.trim()) }) {
                RepositoryCard(
                    repository = repo,
                    modifier = Modifier.fillMaxWidth().width(IntrinsicSize.Max)
                )
            }
        }
    }
}

    