package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.domain.repository.RepositoryRepository

class SearchRepositoriesUseCase(
    private val repository: RepositoryRepository
) {
    suspend operator fun invoke(query: String): List<Repository> {
        return repository.searchRepositories(query)
    }
}