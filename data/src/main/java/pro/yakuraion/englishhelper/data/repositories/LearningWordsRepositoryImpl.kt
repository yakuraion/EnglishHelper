package pro.yakuraion.englishhelper.data.repositories

import pro.yakuraion.englishhelper.data.converters.getLearningWord
import pro.yakuraion.englishhelper.data.converters.getTodayLearningWordEntity
import pro.yakuraion.englishhelper.data.database.daos.TodayLearningWordsDao
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository
import javax.inject.Inject

internal class LearningWordsRepositoryImpl @Inject constructor(
    private val todayLearningWordsDao: TodayLearningWordsDao
) : LearningWordsRepository {

    override suspend fun getTodayWords(): List<LearningWord> {
        return todayLearningWordsDao.getTodayWords()
            .map { getLearningWord(it) }
    }

    override suspend fun setTodayWords(words: List<LearningWord>) {
        val wordsEntities = words.map { getTodayLearningWordEntity(it) }
        todayLearningWordsDao.reset(wordsEntities)
    }

    override suspend fun addTodayWord(word: LearningWord) {
        val wordEntity = getTodayLearningWordEntity(word)
        todayLearningWordsDao.insert(wordEntity)
    }
}
