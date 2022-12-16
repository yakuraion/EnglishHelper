package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.WooordhuntWord
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsExamplesRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository
import javax.inject.Inject

internal class AddWordUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val isWordAlreadyExistUseCase: IsWordAlreadyExistUseCase,
    private val wordsRepository: WordsRepository,
    private val wordsSoundsRepository: WordsSoundsRepository,
    private val wordsExamplesRepository: WordsExamplesRepository,
    private val learningRepository: LearningRepository,
) : AddWordUseCase {

    override suspend fun addWord(
        name: String,
        withExtraInfo: Boolean
    ): AddWordUseCase.Result {
        require(!isWordAlreadyExistUseCase.isWordAlreadyExist(name)) { "Word '$name' has been already added" }
        return withContext(dispatchers.ioDispatcher) {
            val currentDay = learningRepository.getLearningDay()
            if (withExtraInfo) {
                addWordWithExtraInfo(name, currentDay)
            } else {
                addWordWithoutExtraInfo(name, currentDay)
            }
        }
    }

    private suspend fun addWordWithExtraInfo(name: String, currentDay: Int): AddWordUseCase.Result {
        return coroutineScope {
            val wooordhuntWord = wordsRepository.getWooordhuntWord(name)
                ?: return@coroutineScope AddWordUseCase.Result.WORD_NOT_FOUND

            val downloadedSoundUriDeferred = async { getDownloadedSoundUri(wooordhuntWord) }
            val examplesDeferred = async {
                wordsExamplesRepository.getWordsExamples(wooordhuntWord.name, wooordhuntWord.forms)
            }

            val downloadSoundUri = downloadedSoundUriDeferred.await()
            val examples = examplesDeferred.await()

            if (downloadSoundUri == null) {
                return@coroutineScope AddWordUseCase.Result.WORD_NOT_FOUND
            }

            wordsRepository.addNewWord(
                name = name,
                soundUri = downloadSoundUri,
                examples = examples,
                firstDayToLearn = currentDay
            )
            return@coroutineScope AddWordUseCase.Result.SUCCESS
        }
    }

    private suspend fun getDownloadedSoundUri(word: WooordhuntWord): String? {
        return wordsSoundsRepository.downloadSoundForWorld(word.name, word.soundUri)
            ?.toURI()
            ?.toString()
    }

    private suspend fun addWordWithoutExtraInfo(name: String, currentDay: Int): AddWordUseCase.Result {
        wordsRepository.addNewWord(
            name = name,
            soundUri = null,
            examples = emptyList(),
            firstDayToLearn = currentDay
        )
        return AddWordUseCase.Result.SUCCESS
    }
}
