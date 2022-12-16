package pro.yakuraion.englishhelper.data.converters

import com.google.gson.Gson
import pro.yakuraion.englishhelper.data.database.entities.WordEntity
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.entities.learning.WordExample

internal fun getWordEntity(name: String, soundUri: String?, examples: List<WordExample>): WordEntity {
    return WordEntity(
        name = name,
        soundFile = soundUri,
        examplesJson = Gson().toJson(examples)
    )
}

internal fun getWord(entity: WordEntity): Word {
    return Word(
        name = entity.name,
        soundUri = entity.soundFile,
        examples = Gson().fromJson(entity.examplesJson, Array<WordExample>::class.java).toList()
    )
}
