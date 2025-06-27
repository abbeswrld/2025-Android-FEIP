package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.domain.repository.RepositoryRepository

class GetRepositoriesUseCase(
    private val repositoryRepository: RepositoryRepository
) {
    suspend operator fun invoke(page: Int, perPage: Int): List<Repository> {
        return repositoryRepository.getRepositories(page, perPage)
    }
}