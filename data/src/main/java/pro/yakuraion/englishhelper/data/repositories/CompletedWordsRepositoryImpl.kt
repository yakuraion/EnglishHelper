package pro.yakuraion.englishhelper.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.yakuraion.englishhelper.data.converters.getCompletedWord
import pro.yakuraion.englishhelper.data.database.daos.CompletedWordsDao
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import pro.yakuraion.englishhelper.domain.repositories.CompletedWordsRepository
import javax.inject.Inject

internal class CompletedWordsRepositoryImpl @Inject constructor(
    private val completedWordsDao: CompletedWordsDao
) : CompletedWordsRepository {

    override fun getAllWords(): Flow<List<CompletedWord>> {
        return completedWordsDao.getAll()
            .map { list -> list.map { getCompletedWord(it) } }
    }
}
