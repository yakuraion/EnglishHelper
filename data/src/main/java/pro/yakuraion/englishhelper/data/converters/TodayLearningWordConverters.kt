package pro.yakuraion.englishhelper.data.converters

import pro.yakuraion.englishhelper.data.database.entities.TodayLearningWordEntity
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import java.util.*

fun getTodayLearningWordEntity(learningWord: LearningWord): TodayLearningWordEntity {
    return TodayLearningWordEntity(learningWord.word.name, Date().time)
}
