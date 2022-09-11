package pro.yakuraion.englishhelper.data.converters

import pro.yakuraion.englishhelper.data.database.entities.LearningWordEntity
import pro.yakuraion.englishhelper.data.database.entities.WordEntity
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.entities.MemorizationLevel
import pro.yakuraion.englishhelper.domain.entities.Word

internal fun getLearningWordEntity(learningWord: LearningWord): LearningWordEntity {
    val word = WordEntity(learningWord.word.name)
    return LearningWordEntity(word, learningWord.memorizationLevel.level)
}

internal fun getLearningWord(learningWordEntity: LearningWordEntity): LearningWord {
    val word = Word(learningWordEntity.word.name)
    val memorizationLevel = MemorizationLevel(learningWordEntity.memorizationLevel)
    return LearningWord(word, memorizationLevel)
}
