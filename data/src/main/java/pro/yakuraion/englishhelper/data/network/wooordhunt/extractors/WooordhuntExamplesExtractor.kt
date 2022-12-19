package pro.yakuraion.englishhelper.data.network.wooordhunt.extractors

import org.jsoup.nodes.Document
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class WooordhuntExamplesExtractor @Inject constructor() {

    fun extract(html: Document): List<String> {
        return html.getElementsByClass("ex_o")
            .map { it.text().trim() }
    }
}
