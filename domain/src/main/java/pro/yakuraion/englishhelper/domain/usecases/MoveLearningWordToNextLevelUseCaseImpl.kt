package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

internal class MoveLearningWordToNextLevelUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val wordsRepository: WordsRepository,
    private val learningRepository: LearningRepository,
) : MoveLearningWordToNextLevelUseCase {

    override suspend fun moveLearningWordToNextLevel(word: LearningWord) {
        withContext(dispatchers.ioDispatcher) {
            if (word.isMaxLevel()) {
                wordsRepository.completeWord(word)
            } else {
                val currentDay = learningRepository.getLearningDay()
                val newLearningWord = word.increaseLevel(currentDay)
                wordsRepository.updateTodayLearningDay(newLearningWord, false)
            }
        }
    }
}
