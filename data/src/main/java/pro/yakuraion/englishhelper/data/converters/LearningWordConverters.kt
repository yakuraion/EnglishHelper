package pro.yakuraion.englishhelper.data.converters

import pro.yakuraion.englishhelper.data.database.entities.LearningWordEntity
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.MemorizationLevel

internal fun getLearningWordEntity(word: LearningWord): LearningWordEntity {
    return LearningWordEntity(
        name = word.name,
        memorizationLevel = word.memorizationLevel.level,
        nextDayToLearn = word.nextDayToLearn
    )
}

internal fun getLearningWordEntity(name: String, nextDayToLearn: Int): LearningWordEntity {
    return LearningWordEntity(
        name = name,
        memorizationLevel = MemorizationLevel.new().level,
        nextDayToLearn = nextDayToLearn
    )
}

internal fun getLearningWord(entity: LearningWordEntity): LearningWord {
    return LearningWord(
        name = entity.name,
        memorizationLevel = MemorizationLevel(entity.memorizationLevel),
        nextDayToLearn = entity.nextDayToLearn
    )
}
