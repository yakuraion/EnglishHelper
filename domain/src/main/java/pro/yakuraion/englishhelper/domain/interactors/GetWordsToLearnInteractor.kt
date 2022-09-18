package pro.yakuraion.englishhelper.domain.interactors

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.LearningWord

interface GetWordsToLearnInteractor {

    suspend fun getWordsToLearnToday(): Flow<List<LearningWord>>
}
