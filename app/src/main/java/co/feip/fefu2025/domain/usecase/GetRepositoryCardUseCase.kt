package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.data.source.RepositoryCard
import co.feip.fefu2025.domain.repository.RepositoryCardRepository

class GetRepositoryCardUseCase(
    private val repository: RepositoryCardRepository
) {
    suspend operator fun invoke(): RepositoryCard {
        return repository.getRepositoryCard()
    }
}