package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

internal class IsWordAlreadyExistUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val wordsRepository: WordsRepository
) : IsWordAlreadyExistUseCase {

    override suspend fun isWordAlreadyExist(name: String): Boolean {
        return withContext(dispatchers.ioDispatcher) {
            wordsRepository.getWordByName(name) != null
        }
    }
}
