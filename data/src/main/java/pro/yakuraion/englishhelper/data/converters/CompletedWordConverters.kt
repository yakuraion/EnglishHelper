package pro.yakuraion.englishhelper.data.converters

import pro.yakuraion.englishhelper.data.database.entities.CompletedWordEntity
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import java.util.*

internal fun getCompletedWordEntity(name: String): CompletedWordEntity {
    return CompletedWordEntity(
        name = name,
        completedAtMillis = Date().time
    )
}

internal fun getCompletedWord(entity: CompletedWordEntity): CompletedWord {
    return CompletedWord(name = entity.name)
}
