package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

internal class DeleteWordUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val wordsRepository: WordsRepository
) : DeleteWordUseCase {

    override suspend fun deleteWord(name: String) {
        withContext(dispatchers.ioDispatcher) {
            wordsRepository.deleteWord(name)
        }
    }
}
