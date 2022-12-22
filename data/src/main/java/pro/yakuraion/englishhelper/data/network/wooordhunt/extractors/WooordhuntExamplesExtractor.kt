package pro.yakuraion.englishhelper.data.network.wooordhunt.extractors

import org.jsoup.nodes.Document
import pro.yakuraion.englishhelper.domain.entities.WooordhuntWord
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class WooordhuntExamplesExtractor @Inject constructor() {

    fun extract(html: Document): List<WooordhuntWord.Example> {
        val sentences = html.getElementsByClass("ex_o").map { it.text().trim() }
        val ruTranslates = html.getElementsByClass("ex_t").map { it.text().trim() }
        return sentences.zip(ruTranslates) { sentence, ruTranslate ->
            WooordhuntWord.Example(sentence, ruTranslate)
        }
    }
}
