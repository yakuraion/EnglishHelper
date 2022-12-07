package pro.yakuraion.englishhelper.data.repositories.inner

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.yakuraion.englishhelper.data.converters.getCompletedWord
import pro.yakuraion.englishhelper.data.converters.getCompletedWordEntity
import pro.yakuraion.englishhelper.data.database.daos.CompletedWordsDao
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import javax.inject.Inject

internal class InnerCompletedWordsRepository @Inject constructor(
    private val completedWordsDao: CompletedWordsDao
) {

    fun getWords(): Flow<List<CompletedWord>> {
        return completedWordsDao.getAll()
            .map { list -> list.map { getCompletedWord(it) } }
    }

    suspend fun addWord(name: String) {
        val completedWordEntity = getCompletedWordEntity(name)
        completedWordsDao.insert(completedWordEntity)
    }

    suspend fun deleteWord(name: String) {
        completedWordsDao.delete(name)
    }
}
