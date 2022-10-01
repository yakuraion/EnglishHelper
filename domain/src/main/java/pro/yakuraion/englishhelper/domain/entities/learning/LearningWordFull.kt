package pro.yakuraion.englishhelper.domain.entities.learning

import pro.yakuraion.englishhelper.domain.entities.Word

data class LearningWordFull(
    val word: Word,
    val learningWord: LearningWord
)
