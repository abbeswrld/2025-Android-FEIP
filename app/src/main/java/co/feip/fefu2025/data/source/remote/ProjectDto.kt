package co.feip.fefu2025.data.source.remote

import com.google.gson.annotations.SerializedName

// Предполагаемая структура DTO от GitLab API
data class ProjectDto(
    @SerializedName("id") val id: Int,
    @SerializedName("description") val description: String?,
    @SerializedName("name") val name: String,
    @SerializedName("path_with_namespace") val pathWithNamespace: String,
    @SerializedName("star_count") val starCount: Int,
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("owner") val owner: OwnerDto?,
    @SerializedName("starred") val starred: Boolean? // <-- ВАЖНО: Добавляем поле для статуса звезды
)

data class OwnerDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("avatar_url") val avatarUrl: String?
)