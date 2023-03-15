package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

internal class MoveLearningWordToPreviousLevelUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val wordsRepository: WordsRepository,
    private val learningRepository: LearningRepository,
) : MoveLearningWordToPreviousLevelUseCase {

    override suspend fun moveLearningWordToPreviousLevel(word: LearningWord) {
        withContext(dispatchers.ioDispatcher) {
            val currentDay = learningRepository.getLearningDay()
            val newLearningWord = word.decreaseLevel(currentDay)
            val isLearnTodayAgain = newLearningWord.nextDayToLearn == currentDay
            wordsRepository.updateTodayLearningDay(newLearningWord, isLearnTodayAgain)
        }
    }
}
