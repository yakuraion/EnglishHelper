package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import pro.yakuraion.englishhelper.domain.repositories.CompletedWordsRepository
import javax.inject.Inject

internal class GetCompletedWordsUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val completedWordsRepository: CompletedWordsRepository
) : GetCompletedWordsUseCase {

    override suspend fun getCompletedWords(): Flow<List<CompletedWord>> {
        return completedWordsRepository.getAllWords()
            .flowOn(dispatchers.ioDispatcher)
    }
}
