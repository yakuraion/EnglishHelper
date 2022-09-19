package pro.yakuraion.englishhelper.data.repositories

import pro.yakuraion.englishhelper.data.converters.getLearningWord
import pro.yakuraion.englishhelper.data.converters.getLearningWordEntity
import pro.yakuraion.englishhelper.data.database.daos.LearningWordsDao
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

internal class WordsRepositoryImpl @Inject constructor(
    private val learningWordsDao: LearningWordsDao
) : WordsRepository {

    override suspend fun getWordByName(name: String): LearningWord? {
        val entity = learningWordsDao.getByName(name)
        return entity?.let { getLearningWord(it) }
    }

    override suspend fun getWordsByMaxLearningDay(maxLearningDay: Int): List<LearningWord> {
        return learningWordsDao.getByMaxLearningDay(maxLearningDay)
            .map { getLearningWord(it) }
    }

    override suspend fun addWord(learningWord: LearningWord) {
        val entity = getLearningWordEntity(learningWord)
        learningWordsDao.insert(entity)
    }

    override suspend fun updateWord(learningWord: LearningWord) {
        val entity = getLearningWordEntity(learningWord)
        learningWordsDao.update(entity)
    }
}
