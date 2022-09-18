package pro.yakuraion.englishhelper.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.LearningWord

interface TodayLearningWordsRepository {

    fun getTodayWords(): Flow<List<LearningWord>>

    suspend fun setTodayWords(words: List<LearningWord>)

    suspend fun addToTodayWords(word: LearningWord)

    suspend fun removeFromTodayWords(word: LearningWord)
}
