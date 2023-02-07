package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

internal class GetCompletedWordsUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val wordsRepository: WordsRepository
) : GetCompletedWordsUseCase {

    override fun getCompletedWords(): Flow<List<CompletedWord>> {
        return wordsRepository.getCompletedWords()
            .flowOn(dispatchers.ioDispatcher)
    }
}
