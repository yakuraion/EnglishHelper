package pro.yakuraion.englishhelper.domain.entities

data class LearningWord(
    val word: Word,
    val memorizationLevel: MemorizationLevel,
    val nextDayToLearn: Int
)
