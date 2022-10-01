package pro.yakuraion.englishhelper.data.converters

import pro.yakuraion.englishhelper.data.database.entities.CompletedWordEntity
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import java.util.*

internal fun getCompletedWordEntity(word: LearningWord): CompletedWordEntity {
    return CompletedWordEntity(
        name = word.name,
        completedAtMillis = Date().time
    )
}
