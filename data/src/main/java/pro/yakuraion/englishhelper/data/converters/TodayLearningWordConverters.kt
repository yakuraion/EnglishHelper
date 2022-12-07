package pro.yakuraion.englishhelper.data.converters

import pro.yakuraion.englishhelper.data.database.entities.TodayLearningWordEntity
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import java.util.*

internal fun getTodayLearningWordEntity(
    name: String,
    createdAt: Calendar? = null
): TodayLearningWordEntity {
    return TodayLearningWordEntity(
        name = name,
        createdAtMillis = createdAt?.timeInMillis ?: Date().time
    )
}

internal fun getTodayLearningWordEntity(
    word: LearningWord,
    createdAt: Calendar? = null
): TodayLearningWordEntity {
    return TodayLearningWordEntity(
        name = word.name,
        createdAtMillis = createdAt?.timeInMillis ?: Date().time
    )
}
