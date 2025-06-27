package co.feip.fefu2025.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.data.repository.RepositoryImpl
import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.domain.repository.RepositoryRepository
import co.feip.fefu2025.domain.usecase.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RepositoriesUiState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val repositories: List<Repository> = emptyList(),
    val topStarredRepositories: List<Repository> = emptyList(),
    val error: String? = null,
    val canLoadMore: Boolean = true,
    val searchQuery: String = "",
    val isSearching: Boolean = false
    val starredRepositories: List<Repository> = emptyList(),
    val isStarredLoading: Boolean = false,
)


enum class RepoListType {
    ALL, STARRED
}

class RepositoriesViewModel(

    private val repository: RepositoryRepository = RepositoryImpl()
) : ViewModel() {


    private val getRepositoriesUseCase = GetRepositoriesUseCase(repository)
    private val getStarredRepositoriesUseCase = GetStarredRepositoriesUseCase(repository)
    private val getTopStarredRepositoriesUseCase = GetTopStarredRepositoriesUseCase(repository)
    private val searchRepositoriesUseCase = SearchRepositoriesUseCase(repository)
    private val starProjectUseCase = StarProjectUseCase(repository)
    private val unstarProjectUseCase = UnstarProjectUseCase(repository)
    private val getStarredRepositoriesUseCase = GetStarredRepositoriesUseCase(repository)
    private val refreshStarredRepositoriesUseCase = RefreshStarredRepositoriesUseCase(repository)

    private val _uiState = MutableStateFlow(RepositoriesUiState())
    val uiState = _uiState.asStateFlow()

    private var currentPage = 1
    private var searchJob: Job? = null


    init {
        observeStarredRepositories()
    }

    private fun observeStarredRepositories() {
        viewModelScope.launch {
            getStarredRepositoriesUseCase().collect { starred ->
                _uiState.update { it.copy(starredRepositories = starred) }
            }
        }
    }
    fun onMyStarsScreenVisible() {
        viewModelScope.launch {
            _uiState.update { it.copy(isStarredLoading = true) }
            try {
                refreshStarredRepositoriesUseCase()
            } finally {
                _uiState.update { it.copy(isStarredLoading = false) }
            }
        }
    }

    fun loadInitialData() {
        loadRepositories(RepoListType.ALL)
        loadTopStarred()
    }

    fun loadRepositories(type: RepoListType) {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                currentPage = 1
                val result = when (type) {
                    RepoListType.ALL -> getRepositoriesUseCase(currentPage, PER_PAGE)
                    RepoListType.STARRED -> getStarredRepositoriesUseCase(currentPage, PER_PAGE)
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        repositories = result,
                        canLoadMore = result.size == PER_PAGE
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Failed to load data: ${e.message}") }
            }
        }
    }

    fun loadMore(type: RepoListType) {
        if (_uiState.value.isLoadingMore || !_uiState.value.canLoadMore) return

        currentPage++
        _uiState.update { it.copy(isLoadingMore = true) }

        viewModelScope.launch {
            try {
                val newRepos = if (_uiState.value.isSearching) {
                    searchRepositoriesUseCase(_uiState.value.searchQuery, currentPage, PER_PAGE)
                } else {
                    when (type) {
                        RepoListType.ALL -> getRepositoriesUseCase(currentPage, PER_PAGE)
                        RepoListType.STARRED -> getStarredRepositoriesUseCase(currentPage, PER_PAGE)
                    }
                }
                _uiState.update {
                    it.copy(
                        isLoadingMore = false,
                        repositories = it.repositories + newRepos,
                        canLoadMore = newRepos.size == PER_PAGE
                    )
                }
            } catch (e: Exception) {
                currentPage--
                _uiState.update { it.copy(isLoadingMore = false, error = "Failed to load more data.") }
            }
        }
    }

    private fun loadTopStarred() {
        viewModelScope.launch {
            try {
                val topRepos = getTopStarredRepositoriesUseCase()
                _uiState.update { it.copy(topStarredRepositories = topRepos) }
            } catch (e: Exception) {

            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            if (query.isBlank()) {
                _uiState.update { it.copy(isSearching = false) }
                loadRepositories(RepoListType.ALL)
            } else {
                _uiState.update { it.copy(isSearching = true, isLoading = true) }
                try {
                    currentPage = 1
                    val searchResult = searchRepositoriesUseCase(query, currentPage, PER_PAGE)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            repositories = searchResult,
                            canLoadMore = searchResult.size == PER_PAGE
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(isLoading = false, error = "Search failed.") }
                }
            }
        }
    }

    fun toggleStar(repo: Repository) {
        viewModelScope.launch {
            try {

                if (repo.isStarred) {
                    unstarProjectUseCase(repo.id)
                } else {
                    starProjectUseCase(repo.id)
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Action failed.") }
            }
        }
    }
    private fun updateRepositoryState(id: Int, isStarred: Boolean, newStarCount: Int) {
        fun updateList(list: List<Repository>): List<Repository> {
            return list.map {
                if (it.id == id) {
                    it.copy(isStarred = isStarred, stars = newStarCount.toString())
                } else {
                    it
                }
            }
        }

        _uiState.update {
            it.copy(
                repositories = updateList(it.repositories),
                topStarredRepositories = updateList(it.topStarredRepositories)
            )
        }
    }

    companion object {
        private const val PER_PAGE = 20
    }
}