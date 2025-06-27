package co.feip.fefu2025.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.presentation.common.RepositoryItem

@Composable
fun RepositoriesScreen(
    viewModel: RepositoriesViewModel = viewModel(),
    listType: RepoListType,
    title: String
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    // Загрузка данных при первом запуске
    LaunchedEffect(key1 = listType) {
        if (listType == RepoListType.ALL) {
            viewModel.loadInitialData()
        } else {
            viewModel.loadRepositories(RepoListType.STARRED)
        }
    }

    // Логика пагинации
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0
                val totalItemsCount = listState.layoutInfo.totalItemsCount
                if (lastVisibleItemIndex >= totalItemsCount - 5 && totalItemsCount > 0) {
                    viewModel.loadMore(listType)
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(title) })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Поиск показываем только на главном экране
            if (listType == RepoListType.ALL) {
                SearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = { viewModel.onSearchQueryChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Блок с топ-10 "starred" проектов
                    if (listType == RepoListType.ALL && uiState.topStarredRepositories.isNotEmpty() && !uiState.isSearching) {
                        item {
                            Column {
                                Text(
                                    "Top Starred Projects",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                                LazyRow(
                                    contentPadding = PaddingValues(horizontal = 8.dp)
                                ) {
                                    items(uiState.topStarredRepositories) { repo ->
                                        RepositoryItem(
                                            repo = repo,
                                            onStarClick = { viewModel.toggleStar(it) },
                                            modifier = Modifier.width(350.dp) // Фикс. ширина для горизонтального списка
                                        )
                                    }
                                }
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                            }
                        }
                    }

                    // Основной список
                    items(uiState.repositories) { repo ->
                        RepositoryItem(
                            repo = repo,
                            onStarClick = { viewModel.toggleStar(it) }
                        )
                    }

                    // Индикатор загрузки в конце списка
                    if (uiState.isLoadingMore) {
                        item {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text("Search projects...") },
        singleLine = true,
        modifier = modifier
    )
}