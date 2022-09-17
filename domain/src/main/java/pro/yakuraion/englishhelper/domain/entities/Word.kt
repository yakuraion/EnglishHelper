package pro.yakuraion.englishhelper.domain.entities

import java.io.File

data class Word(
    val name: String,
    val soundFile: File?
)
