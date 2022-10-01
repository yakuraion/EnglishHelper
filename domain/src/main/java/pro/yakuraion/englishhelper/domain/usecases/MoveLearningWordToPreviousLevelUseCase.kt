package pro.yakuraion.englishhelper.domain.usecases

import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord

interface MoveLearningWordToPreviousLevelUseCase {

    suspend fun moveLearningWordToPreviousLevel(word: LearningWord)
}
