package pro.yakuraion.englishhelper.domain.entities

import java.io.File
import java.net.URLEncoder

data class Word(
    val name: String,
    val soundFile: File?
) {

    val wooordhuntUrl: String = let {
        val encodedWord = URLEncoder.encode(name, "utf-8")
        "https://wooordhunt.ru/word/$encodedWord"
    }
}
