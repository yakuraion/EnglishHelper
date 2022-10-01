package pro.yakuraion.englishhelper.data.repositories

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.yakuraion.englishhelper.data.converters.getCompletedWordEntity
import pro.yakuraion.englishhelper.data.converters.getLearningWord
import pro.yakuraion.englishhelper.data.converters.getLearningWordEntity
import pro.yakuraion.englishhelper.data.converters.getTodayLearningWordEntity
import pro.yakuraion.englishhelper.data.converters.getWordEntity
import pro.yakuraion.englishhelper.data.database.AppDatabase
import pro.yakuraion.englishhelper.data.database.daos.CompletedWordsDao
import pro.yakuraion.englishhelper.data.database.daos.LearningWordsDao
import pro.yakuraion.englishhelper.data.database.daos.TodayLearningWordsDao
import pro.yakuraion.englishhelper.data.database.daos.WordsDao
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.TodayLearningWordsRepository
import java.io.File
import javax.inject.Inject

internal class TodayLearningWordsRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val wordsDao: WordsDao,
    private val learningWordsDao: LearningWordsDao,
    private val todayLearningWordsDao: TodayLearningWordsDao,
    private val completedWordsDao: CompletedWordsDao
) : TodayLearningWordsRepository {

    override fun getTodayLearningWords(): Flow<List<LearningWord>> {
        return todayLearningWordsDao.getTodayWords()
            .map { list -> list.map { getLearningWord(it) } }
    }

    override suspend fun setTodayLearningWords(words: List<LearningWord>) {
        val entities = words.map { getTodayLearningWordEntity(it) }
        todayLearningWordsDao.reset(entities)
    }

    override suspend fun addWordToLearning(name: String, soundFile: File?, nextDayToLearn: Int) {
        val wordEntity = getWordEntity(name, soundFile)
        val learningWordEntity = getLearningWordEntity(name, nextDayToLearn)
        val todayLearningWordEntity = getTodayLearningWordEntity(name)
        appDatabase.withTransaction {
            wordsDao.insert(wordEntity)
            learningWordsDao.insert(learningWordEntity)
            todayLearningWordsDao.insert(todayLearningWordEntity)
        }
    }

    override suspend fun updateWordAndMoveToTheEndOfToday(word: LearningWord) {
        val todayLearningWordEntity = getTodayLearningWordEntity(word)
        todayLearningWordsDao.update(todayLearningWordEntity)
    }

    override suspend fun updateWordAndRemoveFromToday(word: LearningWord) {
        val learningWordEntity = getLearningWordEntity(word)
        appDatabase.withTransaction {
            learningWordsDao.update(learningWordEntity)
            todayLearningWordsDao.delete(word.name)
        }
    }

    override suspend fun completeWord(word: LearningWord) {
        val completedWordEntity = getCompletedWordEntity(word)
        appDatabase.withTransaction {
            learningWordsDao.delete(word.name)
            completedWordsDao.insert(completedWordEntity)
        }
    }
}
