package pro.yakuraion.englishhelper.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.data.database.entities.LearningWordEntity
import pro.yakuraion.englishhelper.data.database.entities.TodayLearningWordEntity

@Dao
internal interface TodayLearningWordsDao {

    @Query(
        """
        SELECT * FROM learning_word
        INNER JOIN today_learning_word ON today_learning_word.name = learning_word.word_name
        ORDER BY today_learning_word.createdAtMillis
        """
    )
    fun getTodayWords(): Flow<List<LearningWordEntity>>

    @Insert
    suspend fun insert(word: TodayLearningWordEntity)

    @Insert
    suspend fun insert(words: List<TodayLearningWordEntity>)

    @Update
    suspend fun update(word: TodayLearningWordEntity)

    @Query("DELETE FROM today_learning_word WHERE name = :name")
    suspend fun delete(name: String)

    @Query("DELETE FROM today_learning_word")
    suspend fun deleteAll()

    @Transaction
    suspend fun reset(words: List<TodayLearningWordEntity>) {
        deleteAll()
        insert(words)
    }
}
