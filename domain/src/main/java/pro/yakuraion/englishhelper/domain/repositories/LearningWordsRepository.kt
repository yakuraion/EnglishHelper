package pro.yakuraion.englishhelper.domain.repositories

import pro.yakuraion.englishhelper.domain.entities.LearningWord

interface LearningWordsRepository {

    suspend fun getTodayWords(): List<LearningWord>

    suspend fun setTodayWords(words: List<LearningWord>)

    suspend fun addTodayWord(word: LearningWord)
}
