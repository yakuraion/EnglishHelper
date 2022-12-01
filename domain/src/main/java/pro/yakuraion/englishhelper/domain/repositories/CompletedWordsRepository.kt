package pro.yakuraion.englishhelper.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.CompletedWord

interface CompletedWordsRepository {

    fun getAllWords(): Flow<List<CompletedWord>>
}
