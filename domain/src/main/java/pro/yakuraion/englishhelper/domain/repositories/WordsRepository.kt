package pro.yakuraion.englishhelper.domain.repositories

import pro.yakuraion.englishhelper.domain.entities.LearningWord

interface WordsRepository {

    suspend fun getWordByName(name: String): LearningWord?

    suspend fun getWordsByMaxLearningDay(maxLearningDay: Int): List<LearningWord>

    suspend fun addWord(learningWord: LearningWord)
}
