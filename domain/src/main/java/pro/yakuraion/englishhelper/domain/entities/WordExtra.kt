package pro.yakuraion.englishhelper.domain.entities

import kotlinx.collections.immutable.ImmutableList

data class WordExtra(
    val name: String,
    val soundUri: String,
    val examples: ImmutableList<Example>
) {

    data class Example(
        val sentenceWithGap: String,
        val missedWord: String,
        val ruTranslate: String
    )
}
