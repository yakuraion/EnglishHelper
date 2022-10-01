package pro.yakuraion.englishhelper.data.converters

import pro.yakuraion.englishhelper.data.database.entities.WordEntity
import pro.yakuraion.englishhelper.domain.entities.Word
import java.io.File

internal fun getWordEntity(name: String, soundFile: File?): WordEntity {
    return WordEntity(
        name = name,
        soundFile = soundFile?.absolutePath
    )
}

internal fun getWord(entity: WordEntity): Word {
    return Word(
        name = entity.name,
        soundFile = entity.soundFile?.let { File(it) }
    )
}
