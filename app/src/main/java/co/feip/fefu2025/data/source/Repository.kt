package co.feip.fefu2025.data.source

data class Repository(
    val id: Int,
    val name: String,
    val username: String,
    val description: String,
    val stars: String,
    val forks: String,
    val avatarUrl: String?,
    var isStarred: Boolean
)