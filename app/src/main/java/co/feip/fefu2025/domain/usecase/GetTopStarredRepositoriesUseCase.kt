package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.domain.repository.RepositoryRepository

class GetTopStarredRepositoriesUseCase(private val repository: RepositoryRepository) {
    suspend operator fun invoke(): List<Repository> {
        return repository.getTopStarredRepositories()
    }
}