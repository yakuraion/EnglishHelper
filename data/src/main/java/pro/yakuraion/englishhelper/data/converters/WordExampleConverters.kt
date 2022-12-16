package pro.yakuraion.englishhelper.data.converters

import pro.yakuraion.englishhelper.domain.entities.learning.WordExample

internal fun getWordExample(example: String): WordExample {
    return WordExample(example)
}
