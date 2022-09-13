package pro.yakuraion.englishhelper.data.converters

import pro.yakuraion.englishhelper.data.database.entities.LearningWordEntity
import pro.yakuraion.englishhelper.data.database.entities.WordEntity
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.entities.MemorizationLevel
import pro.yakuraion.englishhelper.domain.entities.Word
import java.io.File

internal fun getLearningWordEntity(learningWord: LearningWord): LearningWordEntity {
    val word = learningWord.word.run { WordEntity(name, soundFile?.absolutePath) }
    return LearningWordEntity(word, learningWord.memorizationLevel.level)
}

internal fun getLearningWord(learningWordEntity: LearningWordEntity): LearningWord {
    val word = learningWordEntity.word.run { Word(name, soundFile?.let { File(it) }) }
    val memorizationLevel = MemorizationLevel(learningWordEntity.memorizationLevel)
    return LearningWord(word, memorizationLevel)
}
