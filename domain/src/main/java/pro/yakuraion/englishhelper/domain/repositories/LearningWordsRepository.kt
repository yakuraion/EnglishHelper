package pro.yakuraion.englishhelper.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord

interface LearningWordsRepository {

    fun getAllWords(): Flow<List<LearningWord>>

    suspend fun getWordsAvailableToLearnBy(learningDay: Int): List<LearningWord>
}
