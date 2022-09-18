package pro.yakuraion.englishhelper.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.yakuraion.englishhelper.data.converters.getLearningWord
import pro.yakuraion.englishhelper.data.converters.getTodayLearningWordEntity
import pro.yakuraion.englishhelper.data.database.daos.TodayLearningWordsDao
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.TodayLearningWordsRepository
import javax.inject.Inject

internal class TodayLearningWordsRepositoryImpl @Inject constructor(
    private val todayLearningWordsDao: TodayLearningWordsDao
) : TodayLearningWordsRepository {

    override fun getTodayWords(): Flow<List<LearningWord>> {
        return todayLearningWordsDao.getTodayWords()
            .map { list -> list.map { getLearningWord(it) } }
    }

    override suspend fun setTodayWords(words: List<LearningWord>) {
        val wordsEntities = words.map { getTodayLearningWordEntity(it) }
        todayLearningWordsDao.reset(wordsEntities)
    }

    override suspend fun addToTodayWords(word: LearningWord) {
        val wordEntity = getTodayLearningWordEntity(word)
        todayLearningWordsDao.insert(wordEntity)
    }

    override suspend fun removeFromTodayWords(word: LearningWord) {
        todayLearningWordsDao.delete(word.word.name)
    }
}
