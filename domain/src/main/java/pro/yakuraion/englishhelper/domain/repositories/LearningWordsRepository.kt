package pro.yakuraion.englishhelper.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.LearningWord

interface LearningWordsRepository {

    suspend fun getWordByName(name: String): LearningWord?

    suspend fun getWordsByMaxLearningDay(maxLearningDay: Int): List<LearningWord>

    suspend fun addWord(learningWord: LearningWord, addToToday: Boolean)

    fun getTodayWords(): Flow<List<LearningWord>>

    suspend fun setTodayWords(words: List<LearningWord>)

    suspend fun updateTodayWord(learningWord: LearningWord, removeFromToday: Boolean)
}
