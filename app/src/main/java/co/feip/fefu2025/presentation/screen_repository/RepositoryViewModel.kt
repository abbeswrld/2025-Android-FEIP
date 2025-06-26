package co.feip.fefu2025.presentation.screen_repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.data.source.RepositoryCard
import co.feip.fefu2025.domain.usecase.GetRepositoryCardUseCase
import co.feip.fefu2025.presentation.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RepositoryViewModel(
    private val getRepositoryCardUseCase: GetRepositoryCardUseCase
) : ViewModel() {

    private val _repositoryCard = MutableStateFlow<UiState<RepositoryCard>>(UiState.Loading)
    val repositoryCard: StateFlow<UiState<RepositoryCard>> = _repositoryCard

    init {
        fetchRepositoryCard()
    }

    fun fetchRepositoryCard() {
        viewModelScope.launch {
            _repositoryCard.value = UiState.Loading
            try {
                val card = getRepositoryCardUseCase()
                _repositoryCard.value = UiState.Success(card)
            } catch (e: Exception) {
                _repositoryCard.value = UiState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}