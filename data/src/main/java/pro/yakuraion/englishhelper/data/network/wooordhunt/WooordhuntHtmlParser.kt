package pro.yakuraion.englishhelper.data.network.wooordhunt

import org.jsoup.Jsoup
import pro.yakuraion.englishhelper.data.network.wooordhunt.extractors.WooordhuntExamplesExtractor
import pro.yakuraion.englishhelper.data.network.wooordhunt.extractors.WooordhuntFormsExtractor
import pro.yakuraion.englishhelper.data.network.wooordhunt.extractors.WooordhuntSoundExtractor
import pro.yakuraion.englishhelper.domain.entities.WooordhuntWord
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class WooordhuntHtmlParser @Inject constructor(
    private val soundExtractor: WooordhuntSoundExtractor,
    private val formsExtractor: WooordhuntFormsExtractor,
    private val examplesExtractor: WooordhuntExamplesExtractor
) {

    fun parse(word: String, html: String): WooordhuntWord? {
        val document = Jsoup.parse(html)

        val soundUri = soundExtractor.extract(html)
        val forms = formsExtractor.extract(word, document)
        val examples = examplesExtractor.extract(document)

        return soundUri?.let {
            WooordhuntWord(word, html, soundUri, forms, examples)
        }
    }
}
