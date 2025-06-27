package co.feip.fefu2025.data.source.remote

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("projects")
    suspend fun getProjects(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("starred") starred: Boolean? = null,
        @Query("search") search: String? = null,
        @Query("statistics") statistics: Boolean? = null,
        @Query("order_by") orderBy: String? = null,
        @Query("sort") sort: String? = null
    ): List<ProjectDto>

    @POST("projects/{id}/star")
    suspend fun starProject(@Path("id") projectId: Int): ProjectDto

    @DELETE("projects/{id}/unstar")
    suspend fun unstarProject(@Path("id") projectId: Int)
}