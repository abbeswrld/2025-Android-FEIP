package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.domain.repository.RepositoryRepository

class GetStarredRepositoriesUseCase(private val repository: RepositoryRepository) {
    suspend operator fun invoke(page: Int, perPage: Int): List<Repository> {
        return repository.getStarredRepositories(page, perPage)
    }
}