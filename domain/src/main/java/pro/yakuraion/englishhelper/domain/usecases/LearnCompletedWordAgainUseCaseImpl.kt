package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

internal class LearnCompletedWordAgainUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val wordsRepository: WordsRepository,
    private val learningRepository: LearningRepository
) : LearnCompletedWordAgainUseCase {

    override suspend fun learnCompletedWordAgain(word: CompletedWord) {
        withContext(dispatchers.ioDispatcher) {
            val currentDay = learningRepository.getLearningDay()
            wordsRepository.learnCompletedWordAgain(word.name, firstDayToLearn = currentDay)
        }
    }
}
