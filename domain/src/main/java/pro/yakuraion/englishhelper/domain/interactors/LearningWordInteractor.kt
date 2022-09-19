package pro.yakuraion.englishhelper.domain.interactors

import pro.yakuraion.englishhelper.domain.entities.LearningWord

interface LearningWordInteractor {

    suspend fun moveWordToNextLevel(word: LearningWord)

    suspend fun moveWordToPreviousLevel(word: LearningWord)
}
