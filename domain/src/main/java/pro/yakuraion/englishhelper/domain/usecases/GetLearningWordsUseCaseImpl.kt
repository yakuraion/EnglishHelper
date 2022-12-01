package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository
import javax.inject.Inject

internal class GetLearningWordsUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val learningWordsRepository: LearningWordsRepository
) : GetLearningWordsUseCase {

    override fun getLearningWords(): Flow<List<LearningWord>> {
        return learningWordsRepository.getAllWords()
            .flowOn(dispatchers.ioDispatcher)
    }
}
