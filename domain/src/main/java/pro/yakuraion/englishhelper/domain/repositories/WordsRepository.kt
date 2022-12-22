package pro.yakuraion.englishhelper.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import pro.yakuraion.englishhelper.domain.entities.WooordhuntWord
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.entities.WordExtra
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord

interface WordsRepository {

    suspend fun getWordByName(name: String): Word?

    suspend fun getWordExtraByName(name: String): WordExtra?

    suspend fun downloadWoooordhuntWord(word: String): WooordhuntWord?

    fun getTodayLearningWords(): Flow<List<LearningWord>>

    fun getLearningWords(): Flow<List<LearningWord>>

    suspend fun getLearningWordsAvailableToLearnBy(learningDay: Int): List<LearningWord>

    fun getCompletedWords(): Flow<List<CompletedWord>>

    suspend fun addNewLiteWord(
        name: String,
        firstDayToLearn: Int
    )

    suspend fun addNewWord(
        name: String,
        html: String,
        extra: WordExtra,
        firstDayToLearn: Int
    )

    suspend fun deleteWord(name: String)

    suspend fun completeWord(word: LearningWord)

    suspend fun resetLearningDayProgress(name: String, firstDayToLearn: Int)

    suspend fun learnCompletedWordAgain(name: String, firstDayToLearn: Int)

    suspend fun setTodayLearningWords(words: List<LearningWord>)

    suspend fun updateTodayLearningDay(word: LearningWord, addToTodayLearning: Boolean)
}
