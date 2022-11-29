package pro.yakuraion.englishhelper.domain.entities

import android.net.Uri
import java.io.File
import java.net.URLEncoder

data class Word(
    val name: String,
    val soundFile: File?
) {

    val wooordhuntLink: Uri = let {
        val encodedWord = URLEncoder.encode(name, "utf-8")
        Uri.parse("https://wooordhunt.ru/word/$encodedWord")
    }
}
