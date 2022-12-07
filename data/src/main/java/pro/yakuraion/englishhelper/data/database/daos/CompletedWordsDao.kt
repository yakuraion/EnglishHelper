package pro.yakuraion.englishhelper.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.data.database.entities.CompletedWordEntity

@Dao
internal interface CompletedWordsDao {

    @Query("SELECT * FROM completed_word ORDER BY completedAtMillis")
    fun getAll(): Flow<List<CompletedWordEntity>>

    @Insert
    suspend fun insert(completedWordEntity: CompletedWordEntity)

    @Query("DELETE FROM completed_word WHERE name = :name")
    suspend fun delete(name: String)
}
