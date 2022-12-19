package pro.yakuraion.englishhelper.data.repositories.dbupdaters

import kotlinx.collections.immutable.toImmutableList
import pro.yakuraion.englishhelper.data.network.wooordhunt.WooordhuntParser
import pro.yakuraion.englishhelper.data.repositories.inner.InnerWordsRepository
import pro.yakuraion.englishhelper.domain.entities.WordExample
import javax.inject.Inject

@Suppress("ClassName")
internal class DatabaseUpdaterFrom_1 @Inject constructor(
    private val innerWordsRepository: InnerWordsRepository,
    private val wooordhuntParser: WooordhuntParser
) : DatabaseUpdater {

    override suspend fun update() {
        innerWordsRepository.getAllWords().forEach { word ->
            wooordhuntParser.getWord(word.name)?.let { wooordhuntWord ->
                val wordExamples = wooordhuntWord.examples.mapNotNull { rawSentence ->
                    WordExample.fromFilledSentence(rawSentence, wooordhuntWord.forms)
                }.toImmutableList()
                innerWordsRepository.update(word.copy(examples = wordExamples))
            }
        }
    }
}
