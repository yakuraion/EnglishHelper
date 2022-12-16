package pro.yakuraion.englishhelper.domain.repositories

import pro.yakuraion.englishhelper.domain.entities.learning.WordExample

interface WordsExamplesRepository {

    suspend fun downloadWordsExamples(name: String): List<WordExample>
}
