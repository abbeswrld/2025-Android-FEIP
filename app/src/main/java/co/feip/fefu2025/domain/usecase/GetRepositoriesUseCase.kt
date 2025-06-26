package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.domain.repository.RepositoryRepository

class GetRepositoriesUseCase(
    private val repository: RepositoryRepository
) {

    suspend fun getRepositories(): List<Repository> {
        return repository.getRepositories()
    }

    suspend fun getStarredRepositories(): List<Repository> {
        return repository.getStarredRepositories()
    }
}