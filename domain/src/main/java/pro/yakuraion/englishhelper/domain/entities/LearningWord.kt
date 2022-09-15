package pro.yakuraion.englishhelper.domain.entities

class LearningWord(
    val word: Word,
    val memorizationLevel: MemorizationLevel,
    val nextDayToLearn: Int
)
