package pro.yakuraion.englishhelper.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.data.database.entities.LearningWordEntity

@Dao
internal interface LearningWordsDao {

    @Query("SELECT * FROM learning_word")
    fun getAll(): Flow<List<LearningWordEntity>>

    @Query("SELECT * FROM learning_word WHERE word_name = :name")
    suspend fun getByName(name: String): LearningWordEntity?

    @Insert
    suspend fun insert(word: LearningWordEntity)

    @Query("DELETE FROM learning_word WHERE word_name = :name")
    suspend fun delete(name: String)
}
