package pro.yakuraion.englishhelper.domain.interactors

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.entities.MemorizationLevel
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository
import java.io.File
import javax.inject.Inject

class WordsInteractorImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val learningWordsRepository: LearningWordsRepository,
    private val wordsSoundsRepository: WordsSoundsRepository,
    private val learningRepository: LearningRepository
) : WordsInteractor {

    override suspend fun isWordAlreadyExist(name: String): Boolean {
        return withContext(dispatchers.ioDispatcher) {
            learningWordsRepository.getWordByName(name) != null
        }
    }

    override suspend fun addWord(name: String) {
        require(!isWordAlreadyExist(name)) { "Word '$name' has been already added" }
        withContext(dispatchers.ioDispatcher) {
            val soundFile = wordsSoundsRepository.downloadSoundForWorld(name)
            val word = createLearningWord(name, soundFile)
            learningWordsRepository.addWord(word, true)
        }
    }

    private suspend fun createLearningWord(name: String, soundFile: File?): LearningWord {
        val word = Word(name, soundFile)
        val memorizationLevel = MemorizationLevel.new()
        val nextDayToLearn = learningRepository.getLearningDay()
        return LearningWord(word, memorizationLevel, nextDayToLearn)
    }
}
