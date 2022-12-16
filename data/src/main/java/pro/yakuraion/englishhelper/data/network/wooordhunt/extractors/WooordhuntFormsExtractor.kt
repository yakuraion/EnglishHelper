package pro.yakuraion.englishhelper.data.network.wooordhunt.extractors

import org.jsoup.nodes.Document
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class WooordhuntFormsExtractor @Inject constructor() {

    fun extract(word: String, html: Document): List<String> {
        return listOf(word) +
            html.getElementsByClass("word_form_block")
                .flatMap { it.getElementsByAttribute("href") }
                .map { it.text() }
                .distinct()
    }
}
