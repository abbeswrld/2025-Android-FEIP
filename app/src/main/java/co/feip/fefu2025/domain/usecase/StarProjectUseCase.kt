package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.domain.repository.RepositoryRepository

class StarProjectUseCase(private val repository: RepositoryRepository) {
    suspend operator fun invoke(projectId: Int): Repository {
        return repository.starProject(projectId)
    }
}