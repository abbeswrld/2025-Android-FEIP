package co.feip.fefu2025.data.repository

import co.feip.fefu2025.R
import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.domain.repository.RepositoryRepository
import kotlinx.coroutines.delay
import java.io.IOException
import kotlin.random.Random

class RepositoryImpl : RepositoryRepository {

    private val allRepositories = listOf(
        Repository("abbeswrld", "My repository", "143", "3", R.drawable.me),
        Repository("yshelev", "transcyberwolf", "777", "322", R.drawable.icon_rep1),
        Repository("eMD0gG", "Raphaël Ambrosius Costeau", "2511", "450", R.drawable.icon_rep2),
    )

    private val starredRepositories = listOf(
        Repository("yshelev", "transcyberwolf", "777", "322", R.drawable.icon_rep1),
        Repository("eMD0gG", "Raphaël Ambrosius Costeau", "2511", "450", R.drawable.icon_rep2),
    )

    override suspend fun getRepositories(): List<Repository> {
        delay(2000)
        if (Random.nextBoolean()) {
            throw IOException("Ошибка сети при загрузке репозиториев")
        }
        return allRepositories
    }

    override suspend fun getStarredRepositories(): List<Repository> {
        delay(1000)
        return starredRepositories
    }

    override suspend fun searchRepositories(query: String): List<Repository> {
        delay(500)
        if (query.isBlank()) {
            return emptyList()
        }
        return allRepositories.filter {
            it.username.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        }
    }
}