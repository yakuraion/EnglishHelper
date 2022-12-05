package pro.yakuraion.englishhelper.domain.entities

import java.net.URLEncoder

data class Word(
    val name: String,
    val soundUri: String?
) {

    val wooordhuntUrl: String = let {
        val encodedWord = URLEncoder.encode(name, "utf-8")
        "https://wooordhunt.ru/word/$encodedWord"
    }
}
