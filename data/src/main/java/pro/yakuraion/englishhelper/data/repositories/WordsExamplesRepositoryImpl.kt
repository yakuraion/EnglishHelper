package pro.yakuraion.englishhelper.data.repositories

import pro.yakuraion.englishhelper.data.converters.getWordExample
import pro.yakuraion.englishhelper.data.network.words.WordsService
import pro.yakuraion.englishhelper.domain.entities.learning.WordExample
import pro.yakuraion.englishhelper.domain.repositories.WordsExamplesRepository
import javax.inject.Inject

internal class WordsExamplesRepositoryImpl @Inject constructor(
    private val wordsService: WordsService
) : WordsExamplesRepository {

    override suspend fun downloadWordsExamples(name: String): List<WordExample> {
        return wordsService.getWordsExamples(name).examples.map { getWordExample(it) }
    }
}
