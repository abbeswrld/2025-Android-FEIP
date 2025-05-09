package co.feip.fefu2025.data.repository

import co.feip.fefu2025.R
import co.feip.fefu2025.data.source.Repository
import co.feip.fefu2025.domain.repository.RepositoryRepository

class RepositoryImpl : RepositoryRepository {
    override fun getRepositories(): List<Repository> {
        return listOf(
            Repository("abbeswrld", "My repository", "143", "3", R.drawable.me),
            Repository("yshelev ", "transcyberwolf", "777", "322", R.drawable.icon_rep1),
            Repository("eMD0gG ", " Raphaël Ambrosius Costeau", "2511", "450", R.drawable.icon_rep2),
        )
    }

    override fun getStarredRepositories(): List<Repository> {
        return listOf(
            Repository("yshelev ", "transcyberwolf", "777", "322", R.drawable.icon_rep1),
            Repository("eMD0gG ", " Raphaël Ambrosius Costeau", "2511", "450", R.drawable.icon_rep2),
        )
    }
}