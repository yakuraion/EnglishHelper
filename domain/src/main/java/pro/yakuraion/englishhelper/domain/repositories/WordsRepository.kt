package pro.yakuraion.englishhelper.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.WordExample

interface WordsRepository {

    suspend fun getWordByName(name: String): Word?

    fun getTodayLearningWords(): Flow<List<LearningWord>>

    fun getLearningWords(): Flow<List<LearningWord>>

    suspend fun getLearningWordsAvailableToLearnBy(learningDay: Int): List<LearningWord>

    fun getCompletedWords(): Flow<List<CompletedWord>>

    suspend fun addNewWord(
        name: String,
        soundUri: String?,
        examples: List<WordExample>,
        firstDayToLearn: Int
    )

    suspend fun deleteWord(name: String)

    suspend fun completeWord(word: LearningWord)

    suspend fun resetLearningDayProgress(name: String, firstDayToLearn: Int)

    suspend fun learnCompletedWordAgain(name: String, firstDayToLearn: Int)

    suspend fun setTodayLearningWords(words: List<LearningWord>)

    suspend fun updateTodayLearningDay(word: LearningWord, addToTodayLearning: Boolean)
}
