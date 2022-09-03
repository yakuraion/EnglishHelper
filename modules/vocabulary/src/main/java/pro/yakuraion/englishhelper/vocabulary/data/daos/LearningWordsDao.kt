package pro.yakuraion.englishhelper.vocabulary.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.vocabulary.data.entities.LearningWordEntity

@Dao
interface LearningWordsDao {

    @Query("SELECT * FROM learning_word")
    fun getAll(): Flow<List<LearningWordEntity>>

    @Query("SELECT * FROM learning_word WHERE word_name = :name")
    fun getByName(name: String): LearningWordEntity

    @Insert
    fun insert(word: LearningWordEntity)

    @Query("DELETE FROM learning_word WHERE word_name = :name")
    fun delete(name: String)
}
