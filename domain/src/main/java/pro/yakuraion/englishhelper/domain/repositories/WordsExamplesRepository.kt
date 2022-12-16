package pro.yakuraion.englishhelper.domain.repositories

import pro.yakuraion.englishhelper.domain.entities.WordExample

interface WordsExamplesRepository {

    suspend fun getWordsExamples(name: String, forms: List<String>): List<WordExample>
}
