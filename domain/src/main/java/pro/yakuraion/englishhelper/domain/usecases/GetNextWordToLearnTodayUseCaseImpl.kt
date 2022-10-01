package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWordFull
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

internal class GetNextWordToLearnTodayUseCaseImpl @Inject constructor(
    private val getWordsToLearnUseCase: GetWordsToLearnUseCase,
    private val wordsRepository: WordsRepository
) : GetNextWordToLearnTodayUseCase {

    override suspend fun getNextWordToLearnToday(): Flow<LearningWordFull?> {
        return getWordsToLearnUseCase.getWordsToLearnToday()
            .flatMapLatest { learningWords ->
                val learningWord = learningWords.firstOrNull()
                val word = learningWord?.let { wordsRepository.getWordByName(it.name) }
                val learningWordFull = word?.let { LearningWordFull(word, learningWord) }
                flowOf(learningWordFull)
            }
            .distinctUntilChanged()
    }
}
