package pro.yakuraion.englishhelper.domain.usecases

import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord

interface MoveLearningWordToNextLevelUseCase {

    suspend fun moveLearningWordToNextLevel(word: LearningWord)
}
