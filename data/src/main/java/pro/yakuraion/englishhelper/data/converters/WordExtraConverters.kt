package pro.yakuraion.englishhelper.data.converters

import com.google.gson.Gson
import kotlinx.collections.immutable.toImmutableList
import pro.yakuraion.englishhelper.data.database.entities.WordExtraEntity
import pro.yakuraion.englishhelper.domain.entities.WordExtra

internal fun getWordExtra(entity: WordExtraEntity): WordExtra {
    return WordExtra(
        name = entity.name,
        remoteSoundUri = entity.remoteSoundUri,
        localSoundUri = entity.localSoundUri,
        examples = Gson().fromJson(entity.examplesJson, Array<WordExtra.Example>::class.java)
            .toList()
            .toImmutableList()
    )
}

internal fun getWordExtraEntity(word: WordExtra, html: String): WordExtraEntity {
    return WordExtraEntity(
        name = word.name,
        html = html,
        remoteSoundUri = word.remoteSoundUri,
        localSoundUri = word.localSoundUri,
        examplesJson = Gson().toJson(word.examples)
    )
}
