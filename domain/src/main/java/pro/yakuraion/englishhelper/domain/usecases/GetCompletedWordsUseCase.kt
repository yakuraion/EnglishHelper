package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.CompletedWord

interface GetCompletedWordsUseCase {

    suspend fun getCompletedWords(): Flow<List<CompletedWord>>
}
