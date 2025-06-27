package co.feip.fefu2025.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "starred_repositories")
data class RepositoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val description: String,
    val stars: String,
    val forks: String,
    val avatarUrl: String?
)