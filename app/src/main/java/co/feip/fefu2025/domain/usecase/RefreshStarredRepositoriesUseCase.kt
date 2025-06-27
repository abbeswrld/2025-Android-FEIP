package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.repository.RepositoryRepository

class RefreshStarredRepositoriesUseCase(private val repository: RepositoryRepository) {
    suspend operator fun invoke() {
        repository.refreshStarredRepositories()
    }
}