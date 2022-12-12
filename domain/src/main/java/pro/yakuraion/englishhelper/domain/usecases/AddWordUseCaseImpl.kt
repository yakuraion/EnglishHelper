package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository
import javax.inject.Inject

internal class AddWordUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val isWordAlreadyExistUseCase: IsWordAlreadyExistUseCase,
    private val wordsRepository: WordsRepository,
    private val wordsSoundsRepository: WordsSoundsRepository,
    private val learningRepository: LearningRepository,
) : AddWordUseCase {

    override suspend fun addWord(
        name: String,
        withAudio: Boolean
    ): AddWordUseCase.Result {
        require(!isWordAlreadyExistUseCase.isWordAlreadyExist(name)) { "Word '$name' has been already added" }
        return withContext(dispatchers.ioDispatcher) {
            val currentDay = learningRepository.getLearningDay()
            val soundUri = if (withAudio) {
                wordsSoundsRepository.downloadSoundForWorld(name)?.toURI()?.toString()
                    ?: return@withContext AddWordUseCase.Result.WORD_AUDIO_NOT_FOUND
            } else {
                null
            }
            wordsRepository.addNewWord(name, soundUri, firstDayToLearn = currentDay)
            AddWordUseCase.Result.SUCCESS
        }
    }
}
