package pro.yakuraion.englishhelper.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord

interface TodayLearningWordsRepository {

    fun getTodayLearningWords(): Flow<List<LearningWord>>

    suspend fun setTodayLearningWords(words: List<LearningWord>)

    suspend fun addWordToLearning(name: String, soundUri: String?, nextDayToLearn: Int)

    suspend fun updateWordAndMoveToTheEndOfToday(word: LearningWord)

    suspend fun updateWordAndRemoveFromToday(word: LearningWord)

    suspend fun completeWord(word: LearningWord)
}
