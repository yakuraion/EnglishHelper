package pro.yakuraion.englishhelper.data.converters

import com.google.gson.Gson
import kotlinx.collections.immutable.toImmutableList
import pro.yakuraion.englishhelper.data.database.entities.WordEntity
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.entities.WordExample

internal fun getWordEntity(name: String, soundUri: String?, examples: List<WordExample>): WordEntity {
    return WordEntity(
        name = name,
        soundUri = soundUri,
        examplesJson = Gson().toJson(examples)
    )
}

internal fun getWordEntity(word: Word): WordEntity {
    return getWordEntity(
        name = word.name,
        soundUri = word.soundUri,
        examples = word.examples
    )
}

internal fun getWord(entity: WordEntity): Word {
    return Word(
        name = entity.name,
        soundUri = entity.soundUri,
        examples = Gson().fromJson(entity.examplesJson, Array<WordExample>::class.java).toList().toImmutableList()
    )
}
