package pro.yakuraion.englishhelper.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord

interface WordsRepository {

    suspend fun getWordByName(name: String): Word?

    fun getTodayLearningWords(): Flow<List<LearningWord>>

    fun getLearningWords(): Flow<List<LearningWord>>

    suspend fun getLearningWordsAvailableToLearnBy(learningDay: Int): List<LearningWord>

    fun getCompletedWords(): Flow<List<CompletedWord>>

    suspend fun addNewWord(name: String, soundUri: String?, firstDayToLearn: Int)

    suspend fun updateTodayLearningDay(word: LearningWord, addToTodayLearning: Boolean)

    suspend fun completeWord(word: LearningWord)

    suspend fun setTodayLearningWords(words: List<LearningWord>)
}
