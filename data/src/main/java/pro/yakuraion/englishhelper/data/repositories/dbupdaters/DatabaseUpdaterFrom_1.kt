package pro.yakuraion.englishhelper.data.repositories.dbupdaters

import android.net.Uri
import kotlinx.collections.immutable.toImmutableList
import pro.yakuraion.englishhelper.data.network.wooordhunt.WooordhuntHtmlDownloader
import pro.yakuraion.englishhelper.data.network.wooordhunt.WooordhuntHtmlParser
import pro.yakuraion.englishhelper.data.repositories.inner.InnerWordsExtrasRepository
import pro.yakuraion.englishhelper.data.repositories.inner.InnerWordsRepository
import pro.yakuraion.englishhelper.domain.entities.WordExtra
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository
import javax.inject.Inject

@Suppress("ClassName")
internal class DatabaseUpdaterFrom_1 @Inject constructor(
    private val innerWordsRepository: InnerWordsRepository,
    private val innerWordsExtrasRepository: InnerWordsExtrasRepository,
    private val wordsSoundsRepository: WordsSoundsRepository,
    private val wooordhuntHtmlDownloader: WooordhuntHtmlDownloader,
    private val wooordhuntHtmlParser: WooordhuntHtmlParser
) : DatabaseUpdater {

    override suspend fun update() {
        innerWordsRepository.getAllWords().forEach { word ->
            wooordhuntHtmlDownloader.downloadHtml(word.name)?.let { html ->
                wooordhuntHtmlParser.parse(word.name, html)?.let { wooordhuntWord ->
                    val networkSoundUri = Uri.parse(wooordhuntWord.soundUri)
                    val localFileSoundUri = wordsSoundsRepository.downloadSoundForWorld(word.name, networkSoundUri)
                        ?.toURI()
                        ?.toString()
                    if (localFileSoundUri != null) {
                        val wordExtra = WordExtra(
                            name = wooordhuntWord.name,
                            soundUri = localFileSoundUri,
                            examples = wooordhuntWord.examples
                                .mapNotNull { it.toExtraExample(wooordhuntWord.wordForms) }
                                .toImmutableList()
                        )
                        innerWordsExtrasRepository.addWord(wordExtra, html)
                    }
                }
            }
        }
    }
}
