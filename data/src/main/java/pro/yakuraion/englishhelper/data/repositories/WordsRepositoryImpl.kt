package pro.yakuraion.englishhelper.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun addWord(learningWord: LearningWord) {
        val entity = getLearningWordEntity(learningWord)
        learningWordsDao.insert(entity)
    }

    override fun getWords(): Flow<List<LearningWord>> {
        return learningWordsDao.getAll()
            .map { list -> list.map { getLearningWord(it) } }
    }
}
