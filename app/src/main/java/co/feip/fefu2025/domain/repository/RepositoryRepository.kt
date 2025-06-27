package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.data.source.Repository
import kotlinx.coroutines.flow.Flow

interface RepositoryRepository {
    suspend fun getRepositories(page: Int, perPage: Int): List<Repository>


    fun getStarredRepositories(): Flow<List<Repository>>


    suspend fun refreshStarredRepositories()

    suspend fun getTopStarredRepositories(): List<Repository>
    suspend fun searchRepositories(query: String, page: Int, perPage: Int): List<Repository>
    suspend fun starProject(projectId: Int): Repository
    suspend fun unstarProject(projectId: Int)
}