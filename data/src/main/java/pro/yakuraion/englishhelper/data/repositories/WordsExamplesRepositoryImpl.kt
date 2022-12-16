package pro.yakuraion.englishhelper.data.repositories

import pro.yakuraion.englishhelper.data.network.words.WordsService
import pro.yakuraion.englishhelper.domain.entities.WordExample
import pro.yakuraion.englishhelper.domain.repositories.WordsExamplesRepository
import javax.inject.Inject

internal class WordsExamplesRepositoryImpl @Inject constructor(
    private val wordsService: WordsService
) : WordsExamplesRepository {

    override suspend fun getWordsExamples(name: String, forms: List<String>): List<WordExample> {
        return wordsService.getWordsExamples(name).examples
            .mapNotNull { filledSentence ->
                val sortedForms = forms.sortedByDescending { it.length }
                // verify that we have only one occurrence of a substring
                val foundForm = sortedForms.firstOrNull { filledSentence.split(it).count() == 2 }
                foundForm?.let {
                    val sentenceWithGap = filledSentence.replace(foundForm, REPLACE_SYMBOL)
                    WordExample(sentence = sentenceWithGap, missedWord = foundForm)
                }
            }
    }

    companion object {

        private const val REPLACE_SYMBOL = "%s"
    }
}
