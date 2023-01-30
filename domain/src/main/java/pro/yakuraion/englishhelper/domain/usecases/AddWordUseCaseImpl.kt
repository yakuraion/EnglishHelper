package pro.yakuraion.englishhelper.domain.usecases

import android.net.Uri
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.WooordhuntWord
import pro.yakuraion.englishhelper.domain.entities.WordExtra
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
        withExtra: Boolean
    ): AddWordUseCase.Result {
        require(!isWordAlreadyExistUseCase.isWordAlreadyExist(name)) { "Word '$name' has been already added" }
        return withContext(dispatchers.ioDispatcher) {
            val currentDay = learningRepository.getLearningDay()
            if (withExtra) {
                addWordWithExtra(name, currentDay)
            } else {
                addWordWithoutExtra(name, currentDay)
            }
        }
    }

    private suspend fun addWordWithExtra(name: String, currentDay: Int): AddWordUseCase.Result {
        return coroutineScope {
            val wooordhuntWord = wordsRepository.downloadWoooordhuntWord(name)
                ?: return@coroutineScope AddWordUseCase.Result.WORD_NOT_FOUND

            val soundFileUri = getDownloadedSoundUri(wooordhuntWord)
                ?: return@coroutineScope AddWordUseCase.Result.WORD_NOT_FOUND

            val examples = wooordhuntWord.examples.mapNotNull { it.toExtraExample(wooordhuntWord.wordForms) }
                .toImmutableList()

            val extra = WordExtra(
                name = wooordhuntWord.name,
                soundUri = soundFileUri,
                examples = examples
            )

            wordsRepository.addNewWord(
                name = name,
                html = wooordhuntWord.html,
                extra = extra,
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

    private suspend fun addWordWithoutExtra(name: String, currentDay: Int): AddWordUseCase.Result {
        wordsRepository.addNewLiteWord(
            name = name,
            firstDayToLearn = currentDay
        )
        return AddWordUseCase.Result.SUCCESS
    }
}
