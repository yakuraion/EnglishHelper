package pro.yakuraion.englishhelper.domain.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.entities.MemorizationLevel
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

class WordsInteractorImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val wordsRepository: WordsRepository
) : WordsInteractor {

    override suspend fun isWordAlreadyExist(name: String): Boolean {
        return withContext(dispatchers.ioDispatcher) {
            wordsRepository.getWordByName(name) != null
        }
    }

    override suspend fun addWord(name: String) {
        require(!isWordAlreadyExist(name)) { "Word '$name' has been already added" }
        withContext(dispatchers.ioDispatcher) {
            val word = createLearningWord(name)
            wordsRepository.addWord(word)
        }
    }

    private fun createLearningWord(name: String): LearningWord {
        val word = Word(name)
        val memorizationLevel = MemorizationLevel.new()
        return LearningWord(word, memorizationLevel)
    }

    override fun getWords(): Flow<List<LearningWord>> {
        return wordsRepository.getWords()
            .flowOn(dispatchers.ioDispatcher)
    }
}
