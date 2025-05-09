package co.feip.fefu2025.data.repository

import co.feip.fefu2025.data.source.Language
import co.feip.fefu2025.data.source.RepositoryCard
import co.feip.fefu2025.R
import co.feip.fefu2025.domain.repository.RepositoryCardRepository
import java.text.SimpleDateFormat
import java.util.*

class RepositoryCardImpl(private val username: String) : RepositoryCardRepository {

    override fun getRepositoryCard(): RepositoryCard {
        return when (username) {
            "abbeswrld" -> RepositoryCard(
                username = "abbeswrld",
                description = "My repository",
                languages = listOf(
                    Language("Python", 60f, android.graphics.Color.argb(180, 255, 0, 0)),
                    Language("Kotlin", 5f, android.graphics.Color.argb(255, 66, 170, 255)),
                    Language("C++", 35f, android.graphics.Color.argb(255, 255, 255, 0))
                ),
                createdDate = getFormattedDate(),
                stars = "143",
                forks = "3",
                iconResId = R.drawable.me
            )
            "yshelev" -> RepositoryCard(
                username = "yshelev",
                description = "transcyberwolf",
                languages = listOf(
                    Language("Python", 70f, android.graphics.Color.argb(255, 151, 154, 170)),
                    Language("TypeScript", 30f, android.graphics.Color.argb(255, 0, 0, 0))
                ),
                createdDate = getFormattedDate(),
                stars = "777",
                forks = "322",
                iconResId = R.drawable.icon_rep1
            )
            "eMD0gG" -> RepositoryCard(
                username = "eMD0gG",
                description = "Raphaël Ambrosius Costeau?",
                languages = listOf(
                    Language("Java", 70f, android.graphics.Color.argb(255, 165, 0, 255)),
                    Language("C++", 15f, android.graphics.Color.argb(255, 255, 165, 0)),
                    Language("Python", 15f, android.graphics.Color.argb(255, 0, 128, 0))

                ),
                createdDate = getFormattedDate(),
                stars = "2511",
                forks = "450",
                iconResId = R.drawable.icon_rep2

            )


            else -> RepositoryCard(
                username = username,
                description = "Default repo description",
                languages = listOf(
                    Language("C++", 100f, android.graphics.Color.RED)
                ),
                createdDate = getFormattedDate(),
                stars = "0",
                forks = "0",
                iconResId = R.drawable.android
            )
        }
    }

    private fun getFormattedDate(): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(Date())
    }
}