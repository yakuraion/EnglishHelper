package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.flow.first
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import javax.inject.Inject

internal class GetNextWordToLearnTodayUseCaseImpl @Inject constructor(
    private val getWordsToLearnUseCase: GetWordsToLearnUseCase
) : GetNextWordToLearnTodayUseCase {

    override suspend fun getNextWordToLearnToday(): LearningWord? {
        return getWordsToLearnUseCase.getWordsToLearnToday()
            .first()
            .firstOrNull()
    }
}
