package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord

interface GetWordsToLearnUseCase {

    suspend fun getWordsToLearnToday(): Flow<List<LearningWord>>
}
