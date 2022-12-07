package pro.yakuraion.englishhelper.data.repositories.inner

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.yakuraion.englishhelper.data.converters.getLearningWord
import pro.yakuraion.englishhelper.data.converters.getTodayLearningWordEntity
import pro.yakuraion.englishhelper.data.database.daos.TodayLearningWordsDao
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import javax.inject.Inject

internal class InnerTodayLearningWordsRepository @Inject constructor(
    private val todayLearningWordsDao: TodayLearningWordsDao
) {

    fun getWords(): Flow<List<LearningWord>> {
        return todayLearningWordsDao.getAll()
            .map { list -> list.map { getLearningWord(it) } }
    }

    suspend fun setWords(words: List<LearningWord>) {
        val entities = words.map { getTodayLearningWordEntity(it) }
        todayLearningWordsDao.reset(entities)
    }

    suspend fun addWord(name: String) {
        val entity = getTodayLearningWordEntity(name)
        todayLearningWordsDao.insert(entity)
    }

    suspend fun insertOrUpdate(name: String) {
        val entity = getTodayLearningWordEntity(name)
        todayLearningWordsDao.insertOrUpdate(entity)
    }

    suspend fun updateWord(name: String) {
        val entity = getTodayLearningWordEntity(name)
        todayLearningWordsDao.update(entity)
    }

    suspend fun removeWord(name: String) {
        todayLearningWordsDao.delete(name)
    }
}
