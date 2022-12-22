package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.WordExtra
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

class GetWordExtraUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val wordsRepository: WordsRepository
) : GetWordExtraUseCase {

    override suspend fun getWordExtra(name: String): WordExtra? {
        return withContext(dispatchers.ioDispatcher) {
            wordsRepository.getWordExtraByName(name)
        }
    }
}
