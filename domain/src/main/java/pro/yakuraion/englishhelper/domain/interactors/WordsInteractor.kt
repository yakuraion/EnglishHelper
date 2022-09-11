package pro.yakuraion.englishhelper.domain.interactors

import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.domain.entities.LearningWord

interface WordsInteractor {

    suspend fun isWordAlreadyExist(name: String): Boolean

    suspend fun addWord(name: String)

    fun getWords(): Flow<List<LearningWord>>
}
