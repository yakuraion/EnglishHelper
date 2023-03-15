package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.domain.entities.WordExtra
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository
import java.io.File
import javax.inject.Inject

internal class GetWordExtraUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val wordsRepository: WordsRepository,
    private val wordsSoundsRepository: WordsSoundsRepository
) : GetWordExtraUseCase {

    override suspend fun getWordExtra(name: String): WordExtra? {
        return withContext(dispatchers.ioDispatcher) {
            wordsRepository.getWordExtraByName(name)
                ?.also { syncAudioFileIfNeeded(it) }
        }
    }

    private suspend fun syncAudioFileIfNeeded(extra: WordExtra) {
        val isSoundExists = File(extra.localSoundUri).exists()
        if (!isSoundExists) {
            wordsSoundsRepository.downloadSoundForWorld(extra.name, extra.remoteSoundUri)
        }
    }
}
