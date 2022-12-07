package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.flow.first
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWordFull
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

internal class GetNextWordToLearnTodayUseCaseImpl @Inject constructor(
    private val getWordsToLearnUseCase: GetWordsToLearnUseCase,
    private val wordsRepository: WordsRepository
) : GetNextWordToLearnTodayUseCase {

    override suspend fun getNextWordToLearnToday(): LearningWordFull? {
        val learningWord = getWordsToLearnUseCase.getWordsToLearnToday()
            .first()
            .firstOrNull()
        val word = learningWord?.let { wordsRepository.getWordByName(it.name) }
        return word?.let { LearningWordFull(word, learningWord) }
    }
}
