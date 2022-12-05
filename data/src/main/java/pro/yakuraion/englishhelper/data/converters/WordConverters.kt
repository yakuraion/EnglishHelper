package pro.yakuraion.englishhelper.data.converters

import pro.yakuraion.englishhelper.data.database.entities.WordEntity
import pro.yakuraion.englishhelper.domain.entities.Word

internal fun getWordEntity(name: String, soundUri: String?): WordEntity {
    return WordEntity(
        name = name,
        soundFile = soundUri
    )
}

internal fun getWord(entity: WordEntity): Word {
    return Word(
        name = entity.name,
        soundUri = entity.soundFile
    )
}
