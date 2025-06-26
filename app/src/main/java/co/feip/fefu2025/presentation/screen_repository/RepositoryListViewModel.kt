package co.feip.fefu2025.presentation.screen_repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.domain.usecase.GetRepositoriesUseCase
import co.feip.fefu2025.domain.usecase.SearchRepositoriesUseCase
import co.feip.fefu2025.presentation.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class RepositoryListViewModel(
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
    private val searchRepositoriesUseCase: SearchRepositoriesUseCase // Добавили
) : ViewModel() {

    private val _repositories = MutableStateFlow<UiState<List<Repository>>>(UiState.Loading)
    val repositories: StateFlow<UiState<List<Repository>>> = _repositories

    private val _starred = MutableStateFlow<UiState<List<Repository>>>(UiState.Loading)
    val starred: StateFlow<UiState<List<Repository>>> = _starred

   
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResults = MutableStateFlow<UiState<List<Repository>>>(UiState.Success(emptyList()))
    val searchResults: StateFlow<UiState<List<Repository>>> = _searchResults

    init {
        fetchRepositories()
        fetchStarredRepositories()

    
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .collect { query ->
                    if (query.isBlank()) {
                        _searchResults.value = UiState.Success(emptyList())
                    } else {
                        performSearch(query)
                    }
                }
        }
    }

    fun fetchRepositories() {
        viewModelScope.launch {
            _repositories.value = UiState.Loading
            try {
                _repositories.value = UiState.Success(getRepositoriesUseCase.getRepositories())
            } catch (e: Exception) {
                _repositories.value = UiState.Error(e.message ?: "Ошибка")
            }
        }
    }

    fun fetchStarredRepositories() {
        viewModelScope.launch {
            _starred.value = UiState.Loading
            try {
                _starred.value = UiState.Success(getRepositoriesUseCase.getStarredRepositories())
            } catch (e: Exception) {
                _starred.value = UiState.Error(e.message ?: "Ошибка")
            }
        }
    }


    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _searchResults.value = UiState.Loading
            try {
                val results = searchRepositoriesUseCase(query)
                _searchResults.value = UiState.Success(results)
            } catch (e: Exception) {
                _searchResults.value = UiState.Error(e.message ?: "Ошибка поиска")
            }
        }
    }
}