package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWordFull

interface GetNextWordToLearnTodayUseCase {

    suspend fun getNextWordToLearnToday(): Flow<LearningWordFull?>
}
