package pro.yakuraion.englishhelper.data.converters

import pro.yakuraion.englishhelper.data.database.entities.TodayLearningWordEntity
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import java.util.*

internal fun getTodayLearningWordEntity(word: LearningWord): TodayLearningWordEntity {
    return TodayLearningWordEntity(
        name = word.name,
        createdAtMillis = Date().time
    )
}

internal fun getTodayLearningWordEntity(name: String): TodayLearningWordEntity {
    return TodayLearningWordEntity(
        name = name,
        createdAtMillis = Date().time
    )
}
