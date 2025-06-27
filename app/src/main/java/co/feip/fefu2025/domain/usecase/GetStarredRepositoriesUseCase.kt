package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.domain.repository.RepositoryRepository
import kotlinx.coroutines.flow.Flow

class GetStarredRepositoriesUseCase(private val repository: RepositoryRepository) {
    operator fun invoke(): Flow<List<Repository>> {
        return repository.getStarredRepositories()
    }
}