package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.TodayLearningWordsRepository
import javax.inject.Inject

internal class MoveLearningWordToPreviousLevelUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val todayLearningWordsRepository: TodayLearningWordsRepository,
    private val learningRepository: LearningRepository
) : MoveLearningWordToPreviousLevelUseCase {

    override suspend fun moveLearningWordToPreviousLevel(word: LearningWord) {
        withContext(dispatchers.ioDispatcher) {
            val currentDay = learningRepository.getLearningDay()
            val newLearningWord = word.decreaseLevel(currentDay)
            if (newLearningWord.nextDayToLearn == currentDay) {
                todayLearningWordsRepository.updateWordAndMoveToTheEndOfToday(newLearningWord)
            } else {
                todayLearningWordsRepository.updateWordAndRemoveFromToday(newLearningWord)
            }
        }
    }
}
