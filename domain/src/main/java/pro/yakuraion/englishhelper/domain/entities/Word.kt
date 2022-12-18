package pro.yakuraion.englishhelper.domain.entities

import kotlinx.collections.immutable.ImmutableList

data class Word(
    val name: String,
    val soundUri: String?,
    val examples: ImmutableList<WordExample>
)
