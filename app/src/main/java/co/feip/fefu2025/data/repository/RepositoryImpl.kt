package co.feip.fefu2025.data.repository

import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.data.source.local.RepositoryDao
import co.feip.fefu2025.data.source.remote.ApiClient
import co.feip.fefu2025.data.source.remote.ProjectDto
import co.feip.fefu2025.data.source.toDomain
import co.feip.fefu2025.data.source.toEntity
import co.feip.fefu2025.domain.repository.RepositoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class RepositoryImpl(private val dao: RepositoryDao) : RepositoryRepository {

    private val apiService = ApiClient.apiService


    override suspend fun getRepositories(page: Int, perPage: Int): List<Repository> { ... }
    override suspend fun getTopStarredRepositories(): List<Repository> { ... }
    override suspend fun searchRepositories(query: String, page: Int, perPage: Int): List<Repository> { ... }




    override fun getStarredRepositories(): Flow<List<Repository>> {

        return dao.getStarredRepositories().map { it.toDomain() }
    }

    override suspend fun refreshStarredRepositories() {
        try {

            val projectsDto = apiService.getProjects(starred = true, perPage = 100, page = 1, statistics = true)
            val entities = projectsDto.map { it.toEntity() }
            dao.clearAll()
            dao.insertAll(entities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun starProject(projectId: Int): Repository {
        val projectDto = apiService.starProject(projectId)
        dao.insert(projectDto.toEntity())
        return mapToDomain(listOf(projectDto), isStarredOverride = true).first()
    }

    override suspend fun unstarProject(projectId: Int) {
        apiService.unstarProject(projectId)
        dao.deleteById(projectId)
    }

    private fun mapToDomain(projects: List<ProjectDto>, isStarredOverride: Boolean? = null): List<Repository> { ... }
}