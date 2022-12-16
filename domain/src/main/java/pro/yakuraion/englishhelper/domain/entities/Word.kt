package pro.yakuraion.englishhelper.domain.entities

import pro.yakuraion.englishhelper.domain.entities.learning.WordExample

data class Word(
    val name: String,
    val soundUri: String?,
    val examples: List<WordExample>
)
