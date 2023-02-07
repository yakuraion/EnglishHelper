package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.CompletedWord

interface GetCompletedWordsUseCase {

    fun getCompletedWords(): Flow<List<CompletedWord>>
}
