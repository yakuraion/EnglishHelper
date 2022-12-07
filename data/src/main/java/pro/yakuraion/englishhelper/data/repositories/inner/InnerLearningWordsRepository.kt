package pro.yakuraion.englishhelper.data.repositories.inner

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.yakuraion.englishhelper.data.converters.getLearningWord
import pro.yakuraion.englishhelper.data.converters.getLearningWordEntity
import pro.yakuraion.englishhelper.data.database.daos.LearningWordsDao
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import javax.inject.Inject

internal class InnerLearningWordsRepository @Inject constructor(
    private val learningWordsDao: LearningWordsDao
) {

    fun getWords(): Flow<List<LearningWord>> {
        return learningWordsDao.getAll()
            .map { list -> list.map { getLearningWord(it) } }
    }

    suspend fun getWordsAvailableToLearnBy(learningDay: Int): List<LearningWord> {
        return learningWordsDao.getByMaxLearningDay(learningDay)
            .map { getLearningWord(it) }
    }

    suspend fun addWord(name: String, nextDayToLearn: Int) {
        val entity = getLearningWordEntity(name, nextDayToLearn = nextDayToLearn)
        learningWordsDao.insert(entity)
    }

    suspend fun updateWord(word: LearningWord) {
        val entity = getLearningWordEntity(word)
        learningWordsDao.update(entity)
    }

    suspend fun removeWord(name: String) {
        learningWordsDao.delete(name)
    }
}
