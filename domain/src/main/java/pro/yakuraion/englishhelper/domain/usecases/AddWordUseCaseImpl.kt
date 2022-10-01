package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.TodayLearningWordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository
import javax.inject.Inject

internal class AddWordUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val isWordAlreadyExistUseCase: IsWordAlreadyExistUseCase,
    private val wordsSoundsRepository: WordsSoundsRepository,
    private val todayLearningWordsRepository: TodayLearningWordsRepository,
    private val learningRepository: LearningRepository
) : AddWordUseCase {

    override suspend fun addWord(name: String) {
        require(!isWordAlreadyExistUseCase.isWordAlreadyExist(name)) { "Word '$name' has been already added" }
        withContext(dispatchers.ioDispatcher) {
            val soundFile = wordsSoundsRepository.downloadSoundForWorld(name)
            val currentDay = learningRepository.getLearningDay()
            todayLearningWordsRepository.addWordToLearning(name, soundFile, currentDay)
        }
    }
}
