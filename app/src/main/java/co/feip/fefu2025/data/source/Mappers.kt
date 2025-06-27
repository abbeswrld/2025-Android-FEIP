package co.feip.fefu2025.data.source

import co.feip.fefu2025.data.source.local.RepositoryEntity
import co.feip.fefu2025.data.source.remote.ProjectDto

fun ProjectDto.toEntity(): RepositoryEntity {
    return RepositoryEntity(
        id = this.id,
        name = this.name,
        username = this.pathWithNamespace,
        description = this.description ?: "No description available.",
        stars = this.starCount.toString(),
        forks = this.forksCount.toString(),
        avatarUrl = this.owner?.avatarUrl
    )
}
fun RepositoryEntity.toDomain(): Repository {
    return Repository(
        id = this.id,
        name = this.name,
        username = this.username,
        description = this.description,
        stars = this.stars,
        forks = this.forks,
        avatarUrl = this.avatarUrl,
        isStarred = true
    )
}

fun List<RepositoryEntity>.toDomain(): List<Repository> = this.map { it.toDomain() }