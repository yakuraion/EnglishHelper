package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

internal class ResetLearningWordProgressUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val wordsRepository: WordsRepository,
    private val learningRepository: LearningRepository
) : ResetLearningWordProgressUseCase {

    override suspend fun resetLearningWordProgress(word: LearningWord) {
        withContext(dispatchers.ioDispatcher) {
            val currentDay = learningRepository.getLearningDay()
            wordsRepository.resetLearningDayProgress(word.name, currentDay)
        }
    }
}
