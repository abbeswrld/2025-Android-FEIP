package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.data.source.Repository

interface RepositoryRepository {
    suspend fun getRepositories(): List<Repository>
    suspend fun getStarredRepositories(): List<Repository>
    suspend fun searchRepositories(query: String): List<Repository>
}