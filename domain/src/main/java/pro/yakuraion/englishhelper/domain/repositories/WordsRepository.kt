package pro.yakuraion.englishhelper.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.LearningWord

interface WordsRepository {

    suspend fun getWordByName(name: String): LearningWord?

    suspend fun addWord(learningWord: LearningWord)

    fun getWords(): Flow<List<LearningWord>>
}
