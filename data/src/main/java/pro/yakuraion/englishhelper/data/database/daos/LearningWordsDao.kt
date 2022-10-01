package pro.yakuraion.englishhelper.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import pro.yakuraion.englishhelper.data.database.entities.LearningWordEntity

@Dao
internal interface LearningWordsDao {

    @Query("SELECT * FROM learning_word WHERE name = :name")
    suspend fun getByName(name: String): LearningWordEntity

    @Query("SELECT * FROM learning_word WHERE nextDayToLearn <= :maxLearningDay")
    suspend fun getByMaxLearningDay(maxLearningDay: Int): List<LearningWordEntity>

    @Insert
    suspend fun insert(word: LearningWordEntity)

    @Update
    suspend fun update(word: LearningWordEntity)

    @Query("DELETE FROM learning_word WHERE name = :name")
    suspend fun delete(name: String)
}
