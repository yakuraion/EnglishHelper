package pro.yakuraion.englishhelper.domain.entities.converters

import kotlinx.collections.immutable.toImmutableList
import pro.yakuraion.englishhelper.domain.entities.WooordhuntWord
import pro.yakuraion.englishhelper.domain.entities.WordExtra

fun getWordExtra(word: WooordhuntWord, localSoundUri: String): WordExtra {
    return WordExtra(
        name = word.name,
        remoteSoundUri = word.soundUri,
        localSoundUri = localSoundUri,
        examples = word.examples.mapNotNull { getWordExtraExample(it, word.wordForms) }.toImmutableList()
    )
}

private fun getWordExtraExample(example: WooordhuntWord.Example, wordForms: List<String>): WordExtra.Example? {
    val sortedForms = wordForms.sortedByDescending { it.length }
    val foundForm = sortedForms.firstNotNullOfOrNull { form ->
        val numberOfOccurrences = example.sentence.split(form, ignoreCase = true).count() - 1
        if (numberOfOccurrences == 1) {
            val index = example.sentence.indexOf(form, ignoreCase = true)
            example.sentence.substring(index, index + form.length)
        } else {
            null
        }
    }

    return foundForm?.let {
        val sentenceWithGap = example.sentence.replace(foundForm, "%s")
        WordExtra.Example(
            sentenceWithGap = sentenceWithGap,
            missedWord = foundForm,
            ruTranslate = example.ruTranslate
        )
    }
}
