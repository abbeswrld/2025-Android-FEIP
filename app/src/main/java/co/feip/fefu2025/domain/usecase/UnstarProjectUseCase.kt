package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.repository.RepositoryRepository

class UnstarProjectUseCase(private val repository: RepositoryRepository) {
    suspend operator fun invoke(projectId: Int) {
        return repository.unstarProject(projectId)
    }
}