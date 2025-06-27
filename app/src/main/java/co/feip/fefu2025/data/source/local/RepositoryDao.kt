package co.feip.fefu2025.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM starred_repositories ORDER BY name ASC")
    fun getStarredRepositories(): Flow<List<RepositoryEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repositories: List<RepositoryEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repository: RepositoryEntity)


    @Query("DELETE FROM starred_repositories WHERE id = :projectId")
    suspend fun deleteById(projectId: Int)


    @Query("DELETE FROM starred_repositories")
    suspend fun clearAll()
}