package pro.yakuraion.englishhelper.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.yakuraion.englishhelper.data.converters.getLearningWord
import pro.yakuraion.englishhelper.data.database.daos.LearningWordsDao
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository
import javax.inject.Inject

internal class LearningWordsRepositoryImpl @Inject constructor(
    private val learningWordsDao: LearningWordsDao
) : LearningWordsRepository {

    override fun getAllWords(): Flow<List<LearningWord>> {
        return learningWordsDao.getAll()
            .map { list -> list.map { getLearningWord(it) } }
    }

    override suspend fun getWordsAvailableToLearnBy(learningDay: Int): List<LearningWord> {
        return learningWordsDao.getByMaxLearningDay(learningDay)
            .map { getLearningWord(it) }
    }
}
