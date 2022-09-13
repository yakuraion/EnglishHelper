package pro.yakuraion.englishhelper.domain.interactors

import pro.yakuraion.englishhelper.domain.entities.LearningWord

interface GetWordsToLearnInteractor {

    suspend fun getWordsToLearnToday(): List<LearningWord>

    // todo remove (only for debug)
    suspend fun getCurrentDay(): Int
}
