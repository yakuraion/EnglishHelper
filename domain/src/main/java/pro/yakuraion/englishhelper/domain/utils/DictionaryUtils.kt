package pro.yakuraion.englishhelper.domain.utils

import java.net.URLEncoder

object DictionaryUtils {

    fun getDictionaryUrl(word: String): String {
        val encodedWord = URLEncoder.encode(word, "utf-8")
        return "https://wooordhunt.ru/word/$encodedWord"
    }
}
