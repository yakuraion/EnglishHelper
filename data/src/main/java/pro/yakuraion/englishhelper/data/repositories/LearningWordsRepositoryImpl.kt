package pro.yakuraion.englishhelper.data.repositories

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.yakuraion.englishhelper.data.converters.getLearningWord
import pro.yakuraion.englishhelper.data.converters.getLearningWordEntity
import pro.yakuraion.englishhelper.data.converters.getTodayLearningWordEntity
import pro.yakuraion.englishhelper.data.database.AppDatabase
import pro.yakuraion.englishhelper.data.database.daos.LearningWordsDao
import pro.yakuraion.englishhelper.data.database.daos.TodayLearningWordsDao
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository
import javax.inject.Inject

internal class LearningWordsRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val learningWordsDao: LearningWordsDao,
    private val todayLearningWordsDao: TodayLearningWordsDao
) : LearningWordsRepository {

    override suspend fun getWordByName(name: String): LearningWord? {
        val entity = learningWordsDao.getByName(name)
        return entity?.let { getLearningWord(it) }
    }

    override suspend fun getWordsByMaxLearningDay(maxLearningDay: Int): List<LearningWord> {
        return learningWordsDao.getByMaxLearningDay(maxLearningDay)
            .map { getLearningWord(it) }
    }

    override suspend fun addWord(learningWord: LearningWord, addToToday: Boolean) {
        appDatabase.withTransaction {
            val wordEntity = getLearningWordEntity(learningWord)
            if (addToToday) {
                val todayWordEntity = getTodayLearningWordEntity(learningWord)
                todayLearningWordsDao.insert(todayWordEntity)
            }
            learningWordsDao.insert(wordEntity)
        }
    }

    override fun getTodayWords(): Flow<List<LearningWord>> {
        return todayLearningWordsDao.getTodayWords()
            .map { list -> list.map { getLearningWord(it) } }
    }

    override suspend fun setTodayWords(words: List<LearningWord>) {
        val wordsEntities = words.map { getTodayLearningWordEntity(it) }
        todayLearningWordsDao.reset(wordsEntities)
    }

    override suspend fun updateTodayWord(learningWord: LearningWord, removeFromToday: Boolean) {
        appDatabase.withTransaction {
            val wordEntity = getLearningWordEntity(learningWord)
            learningWordsDao.update(wordEntity)
            if (removeFromToday) {
                todayLearningWordsDao.delete(learningWord.word.name)
            } else {
                val todayWordEntity = getTodayLearningWordEntity(learningWord)
                todayLearningWordsDao.update(todayWordEntity)
            }
        }
    }
}
