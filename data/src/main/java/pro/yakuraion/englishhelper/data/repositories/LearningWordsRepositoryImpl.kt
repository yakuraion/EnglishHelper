package pro.yakuraion.englishhelper.data.repositories

import pro.yakuraion.englishhelper.data.converters.getLearningWord
import pro.yakuraion.englishhelper.data.database.daos.LearningWordsDao
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository
import javax.inject.Inject

internal class LearningWordsRepositoryImpl @Inject constructor(
    private val learningWordsDao: LearningWordsDao
) : LearningWordsRepository {

    override suspend fun getWordsAvailableToLearnBy(learningDay: Int): List<LearningWord> {
        return learningWordsDao.getByMaxLearningDay(learningDay)
            .map { getLearningWord(it) }
    }
}
