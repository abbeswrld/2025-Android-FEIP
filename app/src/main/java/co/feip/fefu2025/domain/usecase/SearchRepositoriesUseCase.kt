package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.domain.repository.RepositoryRepository

class SearchRepositoriesUseCase(
    private val repositoryRepository: RepositoryRepository
) {
    suspend operator fun invoke(query: String, page: Int, perPage: Int): List<Repository> {
        return repositoryRepository.searchRepositories(query, page, perPage)
    }
}